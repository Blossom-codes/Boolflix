package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.repositories.BnhUsersRepository;
import com.core.bingehaven.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final BnhUsersRepository bnhUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        return bnhUsersRepository.findByUsernameOrEmail(identity, identity).orElseThrow(() -> new UsernameNotFoundException("Sorry, "+ identity + " not found"));
    }
}
