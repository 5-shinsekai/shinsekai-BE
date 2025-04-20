package com.example.shinsekai.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Component
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job purchaseDailyAggregationJob;
    private final Job purchaseWeeklyAggregationJob;
    private final Job bestProductJob;
    private final Job cartSoftDeleteJob;


    /*
    * 임시 배치 시간 설정 : 매일 오전 10시마다
    * */
//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 10 * * *")
    public void runPurchaseDailyAggregationJob() {
        try {
            LocalDate aggregationDate = LocalDate.now().minusDays(1); // 집계할 날짜
            String time = LocalDateTime.now().toString(); // 집계하는 날짜

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addLocalDate("aggregationDate", aggregationDate)
                    .toJobParameters();
            jobLauncher.run(purchaseDailyAggregationJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("일일 집계 배치 실행 중 오류 발생", e);
        }
    }

    /*
     * 임시 배치 시간 설정 : 매주 월요일 오전 11시마다
     * */
//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 11 ? * MON")
    public void runPurchaseWeeklyAggregationJob() {
        try {
            LocalDate startDate = LocalDate.now().minusDays(7); // 집계할 날짜
            LocalDate endDate = LocalDate.now().minusDays(1); // 집계할 날짜
            String time = LocalDateTime.now().toString(); // 집계하는 날짜

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addLocalDate("startDate", startDate)
                    .addLocalDate("endDate", endDate)
                    .toJobParameters();
            jobLauncher.run(purchaseWeeklyAggregationJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("주간 집계 배치 실행 중 오류 발생", e);
        }
    }

//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 12 ? * MON")
    public void runBestProductJob() {
        try {
            LocalDate rankDate = LocalDate.now().minusDays(1); // 집계할 날짜
            String time = LocalDateTime.now().toString(); // 집계하는 날짜

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addLocalDate("rankDate", rankDate)
                    .toJobParameters();
            jobLauncher.run(bestProductJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("베스트 상품 배치 실행 중 오류 발생", e);
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

