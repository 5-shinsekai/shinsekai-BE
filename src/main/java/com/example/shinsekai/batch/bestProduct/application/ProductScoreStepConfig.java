package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.ProductScore;
import com.example.shinsekai.batch.bestProduct.infrastructure.ProductScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
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
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProductScoreStepConfig {
    private final DataSource dataSource;
    private final ProductScoreRepository productScoreRepository;
    private final int PAGE_SIZE = 1000;

    @Bean
    @StepScope
    public JdbcPagingItemReader<ProductScore> productScoreReader(@Value("#{jobParameters['rankDate']}") LocalDate rankDate) throws Exception {

        return new JdbcPagingItemReaderBuilder<ProductScore>()
                .name("jdbcPagingItemReader")
                .fetchSize(PAGE_SIZE)
                .dataSource(dataSource) // dataSource만 설정
                .rowMapper(new ProductScoreStepConfig.ProductScoreRowMapper())
                .queryProvider(productScoreQueryProvider())
                .parameterValues(Map.of("rankDate", rankDate))
                .build();
    }

    @Bean
    public ItemWriter<ProductScore> productScoreWriter() {
        return productScoreRepository::saveAll;
    }

    public PagingQueryProvider productScoreQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
        factory.setSelectClause("""
        main_category_id, product_code, product_name,
        total_quantity, total_like,product_score, aggregate_at_end
    """);

        factory.setFromClause("""
    (SELECT pwa.main_category_id, pwa.product_code, pwa.product_name,
            SUM(pwa.quantity) AS total_quantity,
            COUNT(pl.product_code) AS total_like,
            (SUM(pwa.quantity) * 2 + COUNT(pl.product_code)) AS product_score,
            aggregate_at_end
      FROM purchase_weekly_aggregation pwa
      LEFT JOIN product_like pl ON pl.product_code = pwa.product_code
      WHERE pwa.aggregate_at_end = :rankDate
      GROUP BY pwa.main_category_id, pwa.product_code, pwa.product_name) AS sub
""");
        Map<String, Order> sortKeys = new LinkedHashMap<>();
        sortKeys.put("aggregate_at_end", Order.ASCENDING);
        sortKeys.put("main_category_id", Order.ASCENDING);
        sortKeys.put("product_code", Order.ASCENDING);

        factory.setSortKeys(sortKeys);

        return factory.getObject();
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
