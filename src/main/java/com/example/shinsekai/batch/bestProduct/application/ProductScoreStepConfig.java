package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.ProductScore;
import com.example.shinsekai.batch.bestProduct.infrastructure.ProductScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProductScoreStepConfig {
    private final DataSource dataSource;
    private final ProductScoreRepository productScoreRepository;

    @Bean
    @StepScope
    public JdbcCursorItemReader<ProductScore> productScoreReader(
            @Value("#{jobParameters['rankDate']}") LocalDate rankDate
    ) {
        JdbcCursorItemReader<ProductScore> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("""
                SELECT pwa.main_category_id, pwa.product_code, pwa.product_name,
                       SUM(pwa.quantity) AS total_quantity,
                       COUNT(pl.product_code) AS total_like,
                       (SUM(pwa.quantity) * 2 + COUNT(pl.product_code)) AS product_score,
                       pwa.aggregate_at_end
                FROM purchase_weekly_aggregation pwa
                LEFT JOIN product_like pl ON pl.product_code = pwa.product_code
                WHERE pwa.aggregate_at_end = ?
                GROUP BY pwa.main_category_id, pwa.product_code, pwa.product_name, pwa.aggregate_at_end
                ORDER BY pwa.aggregate_at_end, pwa.main_category_id, pwa.product_code
                """);

        reader.setPreparedStatementSetter(new ArgumentPreparedStatementSetter(new Object[]{rankDate}));
        reader.setRowMapper(new ProductScoreStepConfig.ProductScoreRowMapper());
        reader.setVerifyCursorPosition(false);
        reader.setName("jdbcCursorItemReader");

        return reader;
    }

    @Bean
    public ItemWriter<ProductScore> productScoreWriter() {
        return productScoreRepository::saveAll;
    }

    public static class ProductScoreRowMapper implements RowMapper<ProductScore> {
        @Override
        public ProductScore mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ProductScore.builder()
                    .mainCategoryId(rs.getLong("main_category_id"))
                    .productCode(rs.getString("product_code"))
                    .totalQuantity(rs.getLong("total_quantity"))
                    .totalLike(rs.getLong("total_like"))
                    .productScore(rs.getLong("product_score"))
                    .calculationAt(rs.getDate("aggregate_at_end").toLocalDate())
                    .build();
        }
    }
}
