package com.example.shinsekai.batch.common;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicBatchScheduler {

    private final CronService cronService;
    private final BatchScheduler batchScheduler;
    private final TaskScheduler taskScheduler;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();
    private final Map<String, String> currentCrons = new HashMap<>();

    @PostConstruct
    public void init() {
        // 초기 cron 등록
        List<String> jobNames = List.of("purchaseDailyAggregationJob", "purchaseWeeklyAggregationJob", "bestProductJob", "cartSoftDeleteJob");
        for (String jobName : jobNames) {
            String cron = cronService.getCron(jobName);
            if (cron != null) {
                scheduleTask(jobName, cron);
                currentCrons.put(jobName, cron);
            }
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void checkAndUpdateCrons() {
        List<String> jobNames = List.of("purchaseDailyAggregationJob", "purchaseWeeklyAggregationJob", "bestProductJob", "cartSoftDeleteJob");
        for (String jobName : jobNames) {
            String newCron = cronService.getCron(jobName);
            String oldCron = currentCrons.get(jobName);
            if (newCron != null && !newCron.equals(oldCron)) {
                cancelTask(jobName);
                scheduleTask(jobName, newCron);
                currentCrons.put(jobName, newCron);
            }
        }
    }

    private void scheduleTask(String jobName, String cron) {
        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
            switch (jobName) {
                case "purchaseDailyAggregationJob" -> batchScheduler.runPurchaseDailyAggregationJob();
                case "purchaseWeeklyAggregationJob" -> batchScheduler.runPurchaseWeeklyAggregationJob();
                case "bestProductJob" -> batchScheduler.runBestProductJob();
                case "cartSoftDeleteJob" -> batchScheduler.runCartSoftDeleteJob();
                default -> log.warn("등록되지 않은 작업: {}", jobName);
            }
        }, new CronTrigger(cron));

        scheduledTasks.put(jobName, future);
        log.info("스케줄 등록 완료 - jobName: {}, cron: {}", jobName, cron);
    }

    private void cancelTask(String jobName) {
        ScheduledFuture<?> future = scheduledTasks.get(jobName);
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
            log.info("기존 스케줄 취소 완료 - jobName: {}", jobName);
        }
    }
}
