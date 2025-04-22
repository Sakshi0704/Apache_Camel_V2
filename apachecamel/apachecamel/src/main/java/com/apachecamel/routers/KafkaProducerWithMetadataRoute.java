package com.apachecamel.routers;

import com.apachecamel.processor.MetadataProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerWithMetadataRoute extends RouteBuilder {

    @Value("${kafka.topic.name}")
    private String topic;

    @Value("${kafka.bootstrap.servers}")
    private String brokers;

    @Autowired
    private MetadataProcessor metadataProcessor;

    @Override
    public void configure() {
        from("direct:sendWithMeta")
                .log("Original Message: ${body}")
                .process(metadataProcessor)
                .log("Enriched Message to Kafka: ${body}")
                .to(String.format("kafka:%s?brokers=%s", topic, brokers))
                .log("Message with metadata sent to Kafka");
    }

}
