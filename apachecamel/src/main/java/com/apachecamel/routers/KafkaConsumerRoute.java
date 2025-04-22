package com.apachecamel.routers;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRoute extends RouteBuilder {

    @Value("${kafka.topic.name}")
    private String topic;

    @Value("${kafka.bootstrap.servers}")
    private String brokers;

    @Override
    public void configure() {
        onException(Exception.class)
                .maximumRedeliveries(3)
                .redeliveryDelay(2000)
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .log(LoggingLevel.ERROR, "Retrying due to error: ${exception.message}")
                .handled(true);

        from(String.format("kafka:%s?brokers=%s&groupId=myGroup&autoOffsetReset=latest", topic, brokers))
                .routeId("KafkaConsumerRoute")
                .log("Received Kafka message: ${body}")
                /* To check error if message contains "fail"*/
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    if (body.contains("fail")) {
                        throw new RuntimeException("Simulated error for testing retry logic");
                    }
                })
                .transform().simple("[PROCESSED] ${body}")
                .log("Final processed message: ${body}");
    }

}
