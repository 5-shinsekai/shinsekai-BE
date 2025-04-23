package com.example.shinsekai.batch.notification.config;

import com.example.shinsekai.notification.entity.RestockNotification;
import com.example.shinsekai.notification.infrastructure.RestockNotificationRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RestockNotificationConfig {

    private final JobRepository jobRepository;
    private final RestockNotificationRepository restockNotificationRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job deleteExpiredRestockNotificationJob(Step deleteExpiredRestockStep) {
        return new JobBuilder("deleteExpiredRestockNotificationJob", jobRepository)
                .start(deleteExpiredRestockStep)
                .build();
    }

    @Bean
    public Step deleteExpiredRestockStep(
            JpaCursorItemReader<RestockNotification> restockNotificationCursorReader,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("deleteExpiredRestockStep", jobRepository)
                .<RestockNotification, RestockNotification>chunk(100, transactionManager)
                .reader(restockNotificationCursorReader)
                .writer(restockNotificationRepository::deleteAll)
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<RestockNotification> restockNotificationCursorReader(
            @Value("#{T(java.time.LocalDateTime).now()}") LocalDateTime now
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("now", now);

        return new JpaCursorItemReaderBuilder<RestockNotification>()
                .name("restockNotificationCursorReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM RestockNotification r WHERE r.validUntil < :now ORDER BY r.id ASC")
                .parameterValues(parameters)
                .build();
    }
}
