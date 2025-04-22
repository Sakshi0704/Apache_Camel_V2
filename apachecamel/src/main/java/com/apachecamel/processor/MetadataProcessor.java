package com.apachecamel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class MetadataProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String originalMessage = exchange.getIn().getBody(String.class);

        String uniqueId = UUID.randomUUID().toString();
        String timestamp = Instant.now().toString();

        String enrichedMessage = String.format("ID: %s | Time: %s | Message: %s", uniqueId, timestamp, originalMessage);

        exchange.getIn().setBody(enrichedMessage);
    }
}
