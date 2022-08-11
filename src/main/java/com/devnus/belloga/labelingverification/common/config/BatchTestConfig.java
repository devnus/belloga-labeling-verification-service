package com.devnus.belloga.labelingverification.common.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 스프링 배치 테스트를 위한 Config
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing // 배치 환경을 자동 설정
@EntityScan("com.devnus.belloga.labelingverification.verification.domain")
@EnableJpaRepositories("com.devnus.belloga.labelingverification.verification.repository")
@ComponentScan(basePackages = {"com.devnus.belloga.labelingverification.verification.event", "com.devnus.belloga.labelingverification.verification.service"})
@EnableTransactionManagement
public class BatchTestConfig {
}
