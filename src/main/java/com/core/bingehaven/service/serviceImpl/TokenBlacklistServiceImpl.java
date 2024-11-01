package com.core.bingehaven.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

//@Service
public class TokenBlacklistServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a token to the blacklist with an expiration time
    public void addToBlacklist(String token, Timestamp expiryTime) {
        String sql = "INSERT INTO jwt_blacklist (token, expiry_time) VALUES (?, ?)";
        jdbcTemplate.update(sql, token, expiryTime);
    }

    // Check if a token is blacklisted and has not yet expired
    public boolean isBlacklisted(String token) {
        String sql = "SELECT COUNT(*) FROM jwt_blacklist WHERE token = ? AND expiry_time > CURRENT_TIMESTAMP";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, token);
        return count != null && count > 0;
    }

    // (Optional) Clean up expired tokens from the blacklist table
    public void cleanUpExpiredTokens() {
        String sql = "DELETE FROM jwt_blacklist WHERE expiry_time <= CURRENT_TIMESTAMP";
        jdbcTemplate.update(sql);
    }
}
