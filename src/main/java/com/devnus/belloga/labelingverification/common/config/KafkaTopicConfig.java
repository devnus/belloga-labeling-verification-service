package com.devnus.belloga.labelingverification.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    @Value(value = "${app.topic.verification.fail-verify-labeling}")
    private String FAIL_VERIFY_LABELING_TOPIC;

    @Value(value = "${app.topic.verification.success-verify-labeling}")
    private String SUCCESS_VERIFY_LABELING_TOPIC;

    @Value(value = "${app.topic.verification.success-verify-text-label}")
    private String SUCCESS_VERIFY_TEXT_LABEL_TOPIC;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        return new KafkaAdmin(configs);
    }

    /**
     * 같은 이름의 토픽이 등록되어 있다면 동작하지 않음
     * partition 개수, replica 개수는 논의 후 수정
     */
    @Bean
    public NewTopic failVerifyLabelingTopic() {
        return TopicBuilder.name(FAIL_VERIFY_LABELING_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic successVerifyLabelingTopic() {
        return TopicBuilder.name(SUCCESS_VERIFY_LABELING_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic successVerifyTextLabelTopic() {
        return TopicBuilder.name(SUCCESS_VERIFY_TEXT_LABEL_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
