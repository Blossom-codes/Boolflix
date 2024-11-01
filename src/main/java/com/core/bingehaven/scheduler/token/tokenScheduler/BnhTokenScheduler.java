package com.core.bingehaven.scheduler.token.tokenScheduler;

import com.core.bingehaven.scheduler.token.service.BnhTokenProcessor;
import com.core.bingehaven.utils.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BnhTokenScheduler {

    private final BnhTokenProcessor bnhTokenProcessor;

//    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void setExpiredTokens()
    {
        LoggingUtils.DebugInfo("STARTING PROCESS TO FLAG EXPIRED TOKENS {} >>>>>");
        bnhTokenProcessor.setTokenStatus("RESET_PASSWORD");
        LoggingUtils.DebugInfo("ENDING PROCESS OF FLAGGING EXPIRED TOKENS {} <<<<<");
    }
//    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void clearExpiredTokens()
    {
        LoggingUtils.DebugInfo("STARTING PROCESS TO DELETE EXPIRED TOKENS {} >>>>>");
        bnhTokenProcessor.deleteExpiredTokens();
        LoggingUtils.DebugInfo("ENDING PROCESS OF DELETING EXPIRED TOKENS {} <<<<<");
    }

    public void setUserStatus()
    {

    }
}
