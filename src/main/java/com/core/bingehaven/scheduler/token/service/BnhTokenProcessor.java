package com.core.bingehaven.scheduler.token.service;

import com.core.bingehaven.entities.BnhTokens;
import com.core.bingehaven.enums.BnhStatus;
import com.core.bingehaven.repositories.BnhTokenRepository;
import com.core.bingehaven.utils.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BnhTokenProcessor {
    private final BnhTokenRepository bnhTokenRepository;

    public void setTokenStatus(String tokenType) {
        try {
            List<BnhTokens> tokensList = null;

            switch (tokenType.toUpperCase()) {
                case "RESET_PASSWORD":
                    tokensList = bnhTokenRepository.findByStatusAndType(BnhStatus.VALID, tokenType);
                    break;
                default:
                    throw new RuntimeException("Invalid Token Type");
            }


            for (BnhTokens token : tokensList) {
                LocalDateTime expiryDate = token.getExpiryDate();

                if (expiryDate.isBefore(LocalDateTime.now())) {
                    token.setStatus(BnhStatus.EXPIRED);
                }

                bnhTokenRepository.save(token);
            }
        } catch (Exception ex) {
            LoggingUtils.DebugInfo("An Exception occured {} " + ex);
        }

    }

    public void deleteExpiredTokens() {
        try {
            List<BnhTokens> tokensList = bnhTokenRepository.findByStatus(BnhStatus.EXPIRED);
            int count = tokensList.size();
            int i = 1;
            for (BnhTokens token : tokensList) {
                bnhTokenRepository.deleteById(token.getId());
                LoggingUtils.DebugInfo("Successfully cleared " + i + " of " + count + " expired tokens");
                i++;
            }
            LoggingUtils.DebugInfo("Successfully cleared all expired tokens {" + count + "}");
        } catch (Exception ex) {
            LoggingUtils.DebugInfo("An Exception occured {} " + ex);
        }

    }
}
