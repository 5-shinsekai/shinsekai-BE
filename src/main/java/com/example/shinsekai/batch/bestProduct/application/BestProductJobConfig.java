package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.domain.purchaseDailyAggregation;
import com.example.shinsekai.batch.bestProduct.infrastructure.purchaseDailyAggregationRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BestProductJobConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final purchaseDailyAggregationRepository purchaseDailyAggregationRepository;

    private final int chunkSize = 1000;

    // Job 설정
    @Bean
    public Job bestProductJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        Job job = new JobBuilder("bestProductJob", jobRepository)
                .start(bestProductStep(jobRepository, transactionManager))
                .build();
        return job;
    }

    // Step 설정
    @Bean
    public Step bestProductStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("bestProductStep", jobRepository)
                .<purchaseDailyAggregation, purchaseDailyAggregation>chunk(chunkSize, transactionManager)
                .reader(purchaseProductReader()) // ItemReader 설정
                .writer(bestProductWriter()) // ItemWriter 설정
                .transactionManager(transactionManager)
                .build();
    }

    // ItemReader 설정
    @Bean
    public JpaPagingItemReader<purchaseDailyAggregation> purchaseProductReader() {

        LocalDateTime startDate = LocalDate.now().minusDays(6).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().plusDays(1).atStartOfDay();

//        LocalDateTime endDate = LocalDateTime.now(); // 현재 시각
//        LocalDateTime startDate = endDate.minusDays(1); // 24시간 전

        String jpql = String.format(
                "select new %s(ppl.productCode, ppl.productName, pcl.mainCategoryId, SUM(ppl.quantity)) " +
                        "from PurchaseProductList ppl " +
                        "left join ProductCategoryList pcl on ppl.productCode = pcl.productCode " +
                        "where ppl.createdAt >= :startDate and ppl.createdAt < :endDate " +
                        "group by pcl.mainCategoryId, ppl.productCode, ppl.productName  " +
                        "order by pcl.mainCategoryId, ppl.productCode asc",
                purchaseDailyAggregation.class.getName()
        );

        return new JpaPagingItemReaderBuilder<purchaseDailyAggregation>()
                .name("bestProductReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(jpql)
                .parameterValues(Map.of(
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .pageSize(chunkSize)
                .build();
    }

    // ItemProcessor 설정
    @Bean
    public ItemProcessor<purchaseDailyAggregation, BestProduct> itemProcessor() {
        return new BestProductProcessor();
    }


    // ItemWriter 설정
    @Bean
    public ItemWriter<purchaseDailyAggregation> bestProductWriter() {
        return purchaseDailyAggregationRepository::saveAll;
    }
}
