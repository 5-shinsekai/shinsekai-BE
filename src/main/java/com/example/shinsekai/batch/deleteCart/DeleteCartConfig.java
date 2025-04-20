package com.example.shinsekai.batch.deleteCart;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.infrastructure.CartRepository;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeleteCartConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final CartRepository cartRepository;

    private final int chunkSize = 10;

    @Bean
    public Job cartSoftDeleteJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException{
        return new JobBuilder("cartSoftDeleteJob", jobRepository)
                .start(cartSoftDeleteStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step cartSoftDeleteStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("cartSoftDeleteStep", jobRepository)
                .<Cart, Cart>chunk(chunkSize, transactionManager)
                .reader(cartReader())
                .processor(cartSoftDeleteProcessor())
                .writer(cartWriter())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Cart> cartReader(){
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("threshold", LocalDateTime.now().minusMonths(2)); // 2달 전

        return new JpaPagingItemReaderBuilder<Cart>()
                .name("cartReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM Cart c " +
                        "WHERE c.isDeleted = false " +
                        "AND c.createdAt < :threshold " +
                        "ORDER BY c.id")
                .parameterValues(parameterValues)
                .pageSize(chunkSize)
                .build();
    }

    @Bean
    public ItemProcessor<Cart, Cart> cartSoftDeleteProcessor() {
        return cart -> {
            cart.setDeleted();
            return cart;
        };
    }

    @Bean
    public ItemWriter<Cart> cartWriter() {
        return cartRepository::saveAll;
    }
}
