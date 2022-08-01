package com.upgrade.booking.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Kafka configuration class . Creates and returns Kafka Producer Bean
 */
@Configuration
public class KafkaConfig {
    @Value("${kafka.bootstrapServer}")
    private String bootStrapServer;
    @Bean
    public KafkaProducer<String,String> getKafkaProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootStrapServer);
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("linger.ms", 0);
        properties.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("request.timeout.ms", 30000);
        properties.put("timeout.ms", 30000);
        properties.put("max.in.flight.requests.per.connection", 5);
        properties.put("retry.backoff.ms", 5);

        return new KafkaProducer<String, String>(properties);
    }
}
