package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.infrastructure.BestProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BestProductStepConfig {

    private final DataSource dataSource;
    private final BestProductRepository bestProductRepository;
    private final int RANK_SIZE = 30;


    @Bean
    @StepScope
    public JdbcCursorItemReader<BestProduct> bestProductReader(
            @Value("#{jobParameters['rankDate']}") LocalDate rankDate
    ) {
        JdbcCursorItemReader<BestProduct> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("""
            SELECT * FROM (
                SELECT * FROM (
                    SELECT ps.main_category_id, ps.product_code,
                           ROW_NUMBER() OVER (PARTITION BY ps.main_category_id ORDER BY ps.product_score DESC, p.created_at DESC) AS product_rank,
                           ps.calculation_at
                    FROM product_score ps
                    JOIN product p ON p.product_code = ps.product_code
                    WHERE ps.calculation_at = ? AND p.is_deleted = 0
                ) ranked
                WHERE product_rank <= ?
            ) final
            """);
        reader.setPreparedStatementSetter((ps) -> {
            ps.setObject(1, rankDate);
            ps.setInt(2, RANK_SIZE);
        });
        reader.setRowMapper(new BestProductRowMapper());
        reader.setVerifyCursorPosition(false);
        reader.setName("jdbcCursorItemReader");
        return reader;
    }

    @Bean
    public ItemWriter<BestProduct> bestProductWriter() {
        return bestProductRepository::saveAll;
    }

    public static class BestProductRowMapper implements RowMapper<BestProduct> {
        @Override
        public BestProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            return BestProduct.builder()
                    .mainCategoryId(rs.getLong("main_category_id"))
                    .productCode(rs.getString("product_code"))
                    .productRank(rs.getInt("product_rank"))
                    .rankAt(rs.getDate("calculation_at").toLocalDate())
                    .build();
        }
    }
}
