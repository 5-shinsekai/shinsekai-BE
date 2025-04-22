package com.example.shinsekai.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job purchaseDailyAggregationJob;
    private final Job purchaseWeeklyAggregationJob;
    private final Job bestProductJob;
    private final @Lazy Job cartSoftDeleteJob;


//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 10 * * *")
    public void runPurchaseDailyAggregationJob() {
        try {
            log.info("일일 집계 배치 실행 시작");
            LocalDate aggregationDate = LocalDate.now().minusDays(1); // 집계할 날짜
            String time = LocalDateTime.now().toString(); // 집계하는 날짜

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addLocalDate("aggregationDate", aggregationDate)
                    .toJobParameters();
            jobLauncher.run(purchaseDailyAggregationJob, jobParameters);
            log.info("일일 집계 배치 실행 성공");
        } catch (Exception e) {
            throw new RuntimeException("일일 집계 배치 실행 중 오류 발생", e);
        }
    }

//    @Scheduled(cron = "0/15 * * * * *")
    @Scheduled(cron = "0 20 10 * * *")
    public void runPurchaseWeeklyAggregationJob() {
        try {
            log.info("주간 집계 배치 실행 시작");
            LocalDate startDate = LocalDate.now().minusDays(7); // 집계할 날짜
            LocalDate endDate = LocalDate.now().minusDays(1); // 집계할 날짜
            String time = LocalDateTime.now().toString(); // 집계하는 날짜

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addLocalDate("startDate", startDate)
                    .addLocalDate("endDate", endDate)
                    .toJobParameters();
            jobLauncher.run(purchaseWeeklyAggregationJob, jobParameters);
            log.info("주간 집계 배치 실행 성공");
        } catch (Exception e) {
            throw new RuntimeException("주간 집계 배치 실행 중 오류 발생", e);
        }
    }

//    @Scheduled(cron = "0/30 * * * * *")
    @Scheduled(cron = "0 40 10 * * *")
    public void runBestProductJob() {
        try {
            log.info("베스트 상품 배치 실행 시작");
            LocalDate rankDate = LocalDate.now().minusDays(1); // 집계할 날짜
            String time = LocalDateTime.now().toString(); // 집계하는 날짜

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addLocalDate("rankDate", rankDate)
                    .toJobParameters();
            jobLauncher.run(bestProductJob, jobParameters);
            log.info("베스트 상품 배치 실행 성공");
        } catch (Exception e) {
            throw new RuntimeException("베스트 상품 배치 실행 중 오류 발생", e);
        }
    }

    /*
     * 임시 배치 시간 설정 : 매일 오전 4시마다
     * */
    @Scheduled(cron = "0 0 04 * * *")
    public void runCartSoftDeleteJob() {
        String time = LocalDateTime.now().toString();
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", time)
                    .addString("jobName", "cartSoftDeleteJob")
                    .toJobParameters();
            jobLauncher.run(cartSoftDeleteJob, jobParameters);
            log.info("장바구니 자동 삭제 배치 실행 성공");
        } catch (Exception e) {
            throw new RuntimeException("장바구니 자동 삭제 배치 실행 중 오류 발생", e);
        }
    }
}
