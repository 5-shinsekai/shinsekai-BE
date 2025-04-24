package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.domain.ProductScore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class BestProductConfig {

    private final BestProductStepConfig bestProductStepConfig;
    private final ProductScoreStepConfig productScoreStepConfig;
    private final JdbcTemplate jdbcTemplate;
    private final int CHUNK_SIZE = 500;

    @Bean
    public Job bestProductJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        Job job = new JobBuilder("bestProductJob", jobRepository)
                .start(productScoreStep(jobRepository, transactionManager))
                .next(deleteBestProductStep(jobRepository, transactionManager))
                .next(bestProductStep(jobRepository, transactionManager))
                .build();
        return job;
    }

    // Step 1: 가중치 계산
    @Bean
    public Step productScoreStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager) throws Exception{
        return new StepBuilder("productScoreStep", jobRepository)
                .<ProductScore, ProductScore>chunk(CHUNK_SIZE, transactionManager)
                .reader(productScoreStepConfig.productScoreReader(null)) // ItemReader 설정
                .writer(productScoreStepConfig.productScoreWriter()) // ItemWriter 설정
                .transactionManager(transactionManager)
                .build();
    }

    // Step 2: 순위테이블 삭제
    @Bean
    public Step deleteBestProductStep(JobRepository jobRepository,
                                      PlatformTransactionManager transactionManager) {
        return new StepBuilder("deleteBestProductStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    jdbcTemplate.update("DELETE FROM best_product");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    // Step 3: 순위 계산
    @Bean
    public Step bestProductStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("bestProductStep", jobRepository)
                .<BestProduct, BestProduct>chunk(CHUNK_SIZE, transactionManager)
                .reader(bestProductStepConfig.bestProductReader(null)) // ItemReader 설정
                .writer(bestProductStepConfig.bestProductWriter()) // ItemWriter 설정
                .transactionManager(transactionManager)
                .build();
    }
}
