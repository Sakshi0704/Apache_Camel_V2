package com.apachecamel.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageProducerController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Value("${kafka.topic.name}")
    private String kafkaTopic;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBrokers;

    /*
    * API for sending message directly to Kafka
    * Directly send the message to Kafka topic
    * */
    @PostMapping("/send-direct")
    public String sendDirectMessage(@RequestParam String message) {
        String endpointUri = "kafka:" + kafkaTopic + "?brokers=" + kafkaBrokers;
        producerTemplate.sendBody(endpointUri, message);
        return "Message sent directly to Kafka topic: " + kafkaTopic;
    }

    /*
     * API for sending message through Camel producer route (with pre-processing)
     * Send message to the direct route (which will process the message in the KafkaProducerRoute)
     *  */
    @PostMapping("/send-with-route")
    public String sendMessageWithRoute(@RequestParam String message) {
        producerTemplate.sendBody("direct:sendToKafka", message);
        return "Message sent to Kafka topic: " + kafkaTopic + " after processing through route";
    }

    /*
    * Add this inside your existing MessageProducerController
    * Send with metadata through route
    * */
    @PostMapping("/send-with-metadata")
    public String sendMessageWithMetadata(@RequestParam String message) {
        producerTemplate.sendBody("direct:sendWithMeta", message);
        return "Message sent to Kafka via route with metadata.";
    }


}
