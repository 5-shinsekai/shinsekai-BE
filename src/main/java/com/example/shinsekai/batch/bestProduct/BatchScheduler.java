package com.example.shinsekai.batch.bestProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Component
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job bestProductJob;
    private final Job cartSoftDeleteJob;


    /*
    * 임시 배치 시간 설정 : 매일 오전 10시마다
    * */
    @Scheduled(cron = "0 0 10 * * *")
    public void runJob() {
        String time = LocalDateTime.now().toString();
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .toJobParameters();
            jobLauncher.run(bestProductJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("배치 실행 중 오류 발생", e);
        }
    }

    /*
     * 임시 배치 시간 설정 : 매일 오전 10시마다
     * */
    @Scheduled(cron = "0 0 10 * * *")
    public void runCartSoftDeleteJob() {
        String time = LocalDateTime.now().toString();
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addString("jobName", "cartSoftDeleteJob")
                    .toJobParameters();
            jobLauncher.run(cartSoftDeleteJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("배치 실행 중 오류 발생", e);
        }
    }
}

