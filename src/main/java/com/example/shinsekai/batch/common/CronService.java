package com.example.shinsekai.batch.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CronService {

    private final CronRepository cronRepository;  // cron 값을 가져올 리포지토리

    public String getCron(String jobName) {
        log.info("jobName: {}", jobName);
        return cronRepository.findCronByJobName(jobName);  // jobName에 해당하는 cron 값 조회
    }
}