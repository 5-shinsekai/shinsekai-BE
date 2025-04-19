package com.example.shinsekai.batch.PurchaseWeeklyAggregation.application;

import com.example.shinsekai.batch.PurchaseDailyAggregation.domain.purchaseDailyAggregation;
import com.example.shinsekai.batch.PurchaseWeeklyAggregation.domain.purchaseWeeklyAggregation;
import com.example.shinsekai.batch.PurchaseWeeklyAggregation.infrastructure.purchaseWeeklyAggregationRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class purchaseWeeklyAggregationConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final purchaseWeeklyAggregationRepository purchaseWeeklyAggregationRepository;

    private final int chunkSize = 1000;

    // Job 설정
    @Bean
    public Job purchaseWeeklyAggregationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        Job job = new JobBuilder("purchaseWeeklyAggregationJob", jobRepository)
                .start(purchaseWeeklyAggregationStep(jobRepository, transactionManager))
                .build();
        return job;
    }

    // Step 설정
    @Bean
    public Step purchaseWeeklyAggregationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("purchaseWeeklyAggregationStep", jobRepository)
                .<purchaseDailyAggregation, purchaseWeeklyAggregation>chunk(chunkSize, transactionManager)
                .reader(purchaseWeeklyAggregationReader(null,null)) // ItemReader 설정
                .processor(purchaseWeeklyAggregationProcessor(null, null))
                .writer(purchaseWeeklyAggregationWriter()) // ItemWriter 설정
                .transactionManager(transactionManager)
                .build();
    }

    // ItemReader 설정
    @Bean
    @StepScope
    public JpaPagingItemReader<purchaseDailyAggregation> purchaseWeeklyAggregationReader(
            @Value("#{jobParameters['startDate']}") LocalDate startDate,
            @Value("#{jobParameters['endDate']}") LocalDate endDate
    ) {

        String jpql = String.format(
                "select new %s(p.productCode, p.productName, p.mainCategoryId, SUM(p.quantity)) " +
                        "FROM purchaseDailyAggregation p " +
                        "WHERE p.aggregateAt >= :startDate AND p.aggregateAt <= :endDate " +
                        "GROUP BY p.productCode, p.mainCategoryId, p.productName " +
                        "ORDER BY p.productCode",
                purchaseDailyAggregation.class.getName()
        );

        return new JpaPagingItemReaderBuilder<purchaseDailyAggregation>()
                .name("purchaseWeeklyAggregationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(jpql)
                .parameterValues(Map.of(
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .pageSize(chunkSize)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<purchaseDailyAggregation, purchaseWeeklyAggregation> purchaseWeeklyAggregationProcessor(
            @Value("#{jobParameters['startDate']}") LocalDate startDate,
            @Value("#{jobParameters['endDate']}") LocalDate endDate
    ) {
        return daily -> purchaseWeeklyAggregation.builder()
                .productCode(daily.getProductCode())
                .productName(daily.getProductName())
                .mainCategoryId(daily.getMainCategoryId())
                .quantity(daily.getQuantity())
                .aggregateAtStart(startDate)
                .aggregateAtEnd(endDate)
                .build();
    }

    // ItemWriter 설정
    @Bean
    @StepScope
    public ItemWriter<purchaseWeeklyAggregation> purchaseWeeklyAggregationWriter() {
        return purchaseWeeklyAggregationRepository::saveAll;
    }
}
