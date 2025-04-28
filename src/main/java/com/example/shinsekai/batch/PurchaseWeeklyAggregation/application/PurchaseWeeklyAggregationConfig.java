package com.example.shinsekai.batch.PurchaseWeeklyAggregation.application;

import com.example.shinsekai.batch.PurchaseDailyAggregation.domain.PurchaseDailyAggregation;
import com.example.shinsekai.batch.PurchaseWeeklyAggregation.domain.PurchaseWeeklyAggregation;
import com.example.shinsekai.batch.PurchaseWeeklyAggregation.infrastructure.PurchaseWeeklyAggregationRepository;
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
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PurchaseWeeklyAggregationConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final PurchaseWeeklyAggregationRepository purchaseWeeklyAggregationRepository;

    private final int chunkSize = 500;

    // Job 설정
    @Bean
    public Job purchaseWeeklyAggregationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        return new JobBuilder("purchaseWeeklyAggregationJob", jobRepository)
                .start(purchaseWeeklyAggregationStep(jobRepository, transactionManager))
                .build();
    }

    // Step 설정
    @Bean
    public Step purchaseWeeklyAggregationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("purchaseWeeklyAggregationStep", jobRepository)
                .<PurchaseDailyAggregation, PurchaseWeeklyAggregation>chunk(chunkSize, transactionManager)
                .reader(purchaseWeeklyAggregationReader(null, null)) // ItemReader 설정
                .processor(purchaseWeeklyAggregationProcessor(null, null))
                .writer(purchaseWeeklyAggregationWriter()) // ItemWriter 설정
                .transactionManager(transactionManager)
                .build();
    }

    // ItemReader 설정
    @Bean
    @StepScope
    public JpaCursorItemReader<PurchaseDailyAggregation> purchaseWeeklyAggregationReader(
            @Value("#{jobParameters['startDate']}") LocalDate startDate,
            @Value("#{jobParameters['endDate']}") LocalDate endDate
    ) {

        String jpql = String.format(
                "select new %s(p.productCode, p.productName, p.mainCategoryId, SUM(p.quantity)) " +
                        "FROM PurchaseDailyAggregation p " +
                        "WHERE p.aggregateAt >= :startDate AND p.aggregateAt <= :endDate " +
                        "GROUP BY p.productCode, p.mainCategoryId, p.productName " +
                        "ORDER BY p.productCode",
                PurchaseDailyAggregation.class.getName()
        );


        JpaCursorItemReader<PurchaseDailyAggregation> reader = new JpaCursorItemReader<>();
        reader.setName("purchaseWeeklyAggregationReader");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString(jpql);
        reader.setParameterValues(Map.of(
                "startDate", startDate,
                "endDate", endDate
        ));

        return reader;
    }


    @Bean
    @StepScope
    public ItemProcessor<PurchaseDailyAggregation, PurchaseWeeklyAggregation> purchaseWeeklyAggregationProcessor(
            @Value("#{jobParameters['startDate']}") LocalDate startDate,
            @Value("#{jobParameters['endDate']}") LocalDate endDate
    ) {
        return daily -> PurchaseWeeklyAggregation.builder()
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
    public ItemWriter<PurchaseWeeklyAggregation> purchaseWeeklyAggregationWriter() {
        return purchaseWeeklyAggregationRepository::saveAll;
    }
}
