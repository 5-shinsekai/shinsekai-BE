package com.example.shinsekai.batch.PurchaseDailyAggregation.application;

import com.example.shinsekai.batch.PurchaseDailyAggregation.domain.PurchaseDailyAggregation;
import com.example.shinsekai.batch.PurchaseDailyAggregation.infrastructure.PurchaseDailyAggregationRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
public class PurchaseDailyAggregationConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final PurchaseDailyAggregationRepository purchaseDailyAggregationRepository;
    private final int chunkSize = 200;

    // Job 설정
    @Bean
    public Job purchaseDailyAggregationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        Job job = new JobBuilder("purchaseDailyAggregationJob", jobRepository)
                .start(purchaseDailyAggregationStep(jobRepository, transactionManager))
                .build();
        return job;
    }

    // Step 설정
    @Bean
    public Step purchaseDailyAggregationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("purchaseDailyAggregationStep", jobRepository)
                .<PurchaseDailyAggregation, PurchaseDailyAggregation>chunk(chunkSize, transactionManager)
                .reader(purchaseDailyAggregationReader(null)) // ItemReader 설정
                .writer(purchaseDailyAggregationWriter(null)) // ItemWriter 설정
                .transactionManager(transactionManager)
                .build();
    }

    // ItemReader 설정
    @Bean
    @StepScope
    public JpaPagingItemReader<PurchaseDailyAggregation> purchaseDailyAggregationReader(@Value("#{jobParameters['aggregationDate']}") LocalDate aggregationDate) {

        LocalDateTime startDate = aggregationDate.atStartOfDay();
        LocalDateTime endDate = aggregationDate.plusDays(1).atStartOfDay();
        String jpql = String.format(
                "select new %s(ppl.productCode, ppl.productName, pcl.mainCategoryId, SUM(ppl.quantity)) " +
                        "from PurchaseProductList ppl " +
                        "left join ProductCategoryList pcl on ppl.productCode = pcl.productCode " +
                        "where ppl.createdAt >= :startDate and ppl.createdAt < :endDate " +
                        "group by pcl.mainCategoryId, ppl.productCode, ppl.productName  " +
                        "order by pcl.mainCategoryId, ppl.productCode asc",
                PurchaseDailyAggregation.class.getName()
        );

        return new JpaPagingItemReaderBuilder<PurchaseDailyAggregation>()
                .name("purchaseDailyAggregationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(jpql)
                .parameterValues(Map.of(
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .pageSize(chunkSize)
                .build();
    }

    // ItemWriter 설정
    @Bean
    @StepScope
    public ItemWriter<PurchaseDailyAggregation> purchaseDailyAggregationWriter(@Value("#{jobParameters['aggregationDate']}") LocalDate aggregationDate) {

        return items -> {
            for (PurchaseDailyAggregation item : items) {
                item.setAggregateAt(aggregationDate);
            }
            purchaseDailyAggregationRepository.saveAll(items);
        };
    }
}
