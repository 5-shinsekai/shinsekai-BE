package com.example.shinsekai.batch.common;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CronRepository extends JpaRepository<CronEntity, Long> {

    @Query("SELECT c.cronExpression FROM CronEntity c WHERE c.jobName = :jobName")
    String findCronByJobName(@Param("jobName") String jobName);
}
