package com.core.bingehaven.scheduler.media;

import com.core.bingehaven.scheduler.media.service.MediaProcessor;
import com.core.bingehaven.service.IMDbService;
import com.core.bingehaven.utils.LoggingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class fetchMediaScheduler {

    private final MediaProcessor mediaProcessor;

    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void fetchMoviesFromImdb() {
        LoggingUtils.DebugInfo("Running Scheduled Movie and Tv Shows Fetch >>>>>");
        mediaProcessor.fetchPopularMoviesFromImdb();
        LoggingUtils.DebugInfo("Ending Scheduled Movie and Tv Shows Fetch <<<<<");
    }
}
