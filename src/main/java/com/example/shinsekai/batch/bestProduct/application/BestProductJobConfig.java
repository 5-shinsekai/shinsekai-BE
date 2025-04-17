package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.infrastructure.BestProductRepository;
import com.example.shinsekai.batch.bestProduct.domain.PurchaseProductCategory;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BestProductJobConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final BestProductRepository bestProductRepository;

    private final int chunkSize = 10;

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
                .<PurchaseProductCategory, BestProduct>chunk(chunkSize, transactionManager) // <InputType, OutputType> 지정
                .reader(purchaseProductReader()) // ItemReader 설정
                .processor(itemProcessor()) // ItemProcessor 설정
                .writer(bestProductWriter()) // ItemWriter 설정
                .transactionManager(transactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        // 배치 시작 전에 작업할 내용
                        //집계 테이블 비우기?
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        // Step이 끝난 후에 BestProductProcessor에서 집계된 결과 가져오기
                        BestProductProcessor processor = (BestProductProcessor) itemProcessor();
                        List<BestProduct> aggregatedResults = processor.getAggregatedResults();
                        log.info("Aggregated Results: {}", aggregatedResults);

                        // 후처리 (예: DB에 저장)
                        bestProductRepository.saveAll(aggregatedResults); // DB에 저장

                        return ExitStatus.COMPLETED; // Step 완료
                    }
                })
                .build();
    }

    // ItemReader 설정
    @Bean
    public JpaPagingItemReader<PurchaseProductCategory> purchaseProductReader() {

        LocalDateTime startDate = LocalDate.now().minusDays(6).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().plusDays(1).atStartOfDay();

        return new JpaPagingItemReaderBuilder<PurchaseProductCategory>()
                .name("bestProductReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select new com.example.shinsekai.batch.bestProduct.domain.PurchaseProductCategory(\n" +
                        "    ppl.productCode,\n" +
                        "    ppl.productName,\n" +
                        "    pcl.mainCategoryId,\n" +
                        "    ppl.quantity\n" +
                        ")\n" +
                        "from PurchaseProductList ppl\n" +
                        "left join ProductCategoryList pcl on ppl.productCode = pcl.productCode\n" +
                        "where ppl.createdAt >= :startDate and ppl.createdAt < :endDate\n"+
                        "order by ppl.id asc")
                .parameterValues(Map.of(
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .pageSize(chunkSize)
                .build();
    }

    // ItemProcessor 설정
    @Bean
    public ItemProcessor<PurchaseProductCategory, BestProduct> itemProcessor() {
        return new BestProductProcessor();
    }

    // ItemWriter 설정
    @Bean
    public ItemWriter<BestProduct> bestProductWriter() {
        return bestProducts -> {
            log.info("BestProduct size: {}", bestProducts.size());
        };
    }
}
