package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.infrastructure.BestProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BestProductStepConfig {

    private final DataSource dataSource;
    private final BestProductRepository bestProductRepository;
    private final int PAGE_SIZE = 1000;
    private final int RANK_SIZE = 30;

    @Bean
    @StepScope
    public JdbcPagingItemReader<BestProduct> bestProductReader(@Value("#{jobParameters['rankDate']}") LocalDate rankDate) throws Exception {
        return new JdbcPagingItemReaderBuilder<BestProduct>()
                .name("jdbcPagingItemReader")
                .fetchSize(PAGE_SIZE)
                .dataSource(dataSource) // dataSource만 설정
                .rowMapper(new BestProductRowMapper())
                .queryProvider(bestProductQueryProvider())
                .parameterValues(Map.of(
                        "1", rankDate,
                        "2", RANK_SIZE
                ))
                .build();
    }

    @Bean
    public ItemWriter<BestProduct> bestProductWriter() {
        return bestProductRepository::saveAll;
    }

    public PagingQueryProvider bestProductQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
        factory.setSelectClause("SELECT *");
        factory.setFromClause("FROM ( " +
                "SELECT * FROM ( " +
                "SELECT ps.main_category_id, ps.product_code, " +
                "ROW_NUMBER() OVER (PARTITION BY ps.main_category_id ORDER BY ps.product_score DESC, p.created_at DESC) AS product_rank, " +
                "ps.calculation_at " +
                "FROM product_score ps " +
                "JOIN product p ON p.product_code = ps.product_code " +
                "WHERE ps.calculation_at = ? AND p.is_deleted = 0" +
                ") ranked " +
                "WHERE product_rank <= ?" +
                ") final");

        factory.setSortKey("product_code");
        return factory.getObject();
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
