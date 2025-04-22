package com.apachecamel.routers;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerRoute extends RouteBuilder {

    @Value("${kafka.topic.name}")
    private String topic;

    @Value("${kafka.bootstrap.servers}")
    private String brokers;

    @Override
    public void configure() {
        from("direct:sendToKafka")
                .log("Preparing message for Kafka: ${body}")
                .transform().simple("Processed message: ${body}")
                .to(String.format("kafka:%s?brokers=%s", topic, brokers))
                .log("Message sent to Kafka: ${body}");
    }

}
