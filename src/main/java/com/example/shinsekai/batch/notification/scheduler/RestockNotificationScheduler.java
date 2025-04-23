package com.example.shinsekai.batch.notification.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestockNotificationScheduler {

    private final JobLauncher jobLauncher;
    private final @Lazy Job deleteExpiredRestockNotificationJob;

    @Scheduled(cron = "0 0 9 * * *")
    public void runRestockNotificationJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", LocalDateTime.now().toString())
                    .toJobParameters();

            jobLauncher.run(deleteExpiredRestockNotificationJob, jobParameters);
            log.info("[Scheduler] 재입고 알림 삭제 Job이 실행되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("재입고 알림 삭제 배치 실행 실패", e);
        }
    }
}
