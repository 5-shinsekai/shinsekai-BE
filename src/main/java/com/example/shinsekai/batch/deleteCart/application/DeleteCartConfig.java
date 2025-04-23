package com.example.shinsekai.batch.deleteCart.application;

import com.example.shinsekai.batch.deleteCart.mapper.CartRowMapper;
import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.infrastructure.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeleteCartConfig {

    private final CartRepository cartRepository;
    private final DataSource dataSource;

    private final int chunkSize = 100;

    @Bean
    public Job cartSoftDeleteJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        return new JobBuilder("cartSoftDeleteJob", jobRepository)
                .start(cartSoftDeleteStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step cartSoftDeleteStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("cartSoftDeleteStep", jobRepository)
                .<Cart, Cart>chunk(chunkSize, transactionManager)
                .reader(cartReader())
                .writer(cartWriter())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Cart> cartReader() {
        JdbcCursorItemReader<Cart> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM cart WHERE is_deleted = false AND created_at < ? ORDER BY created_at, id");
        reader.setPreparedStatementSetter(
                ps -> ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusMonths(1))));
        reader.setRowMapper(new CartRowMapper());
        return reader;
    }

    @Bean
    public ItemWriter<Cart> cartWriter() {
        return items -> {
            for (Cart item : items) {
                cartRepository.findById(item.getId()).ifPresent(cart -> {
                    cart.setDeleted();
                    cartRepository.save(cart);
                });
            }
            cartRepository.flush();
        };
    }
}
