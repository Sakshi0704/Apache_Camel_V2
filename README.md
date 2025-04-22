# Apache Camel with Kafka Integration & Spring Boot

This project demonstrates a seamless integration between **Apache Camel**, **Kafka**, and **Spring Boot**, allowing message routing and transformation between REST APIs and Kafka topics. The application is built to showcase how you can produce and consume messages using Apache Camel routes and processors.

## 🚀 Features

- REST API to send messages directly to Kafka
- Camel-based routing for Kafka consumer
- Kafka topic configured via `application.properties`
- Custom message processing using a Camel Processor

## 🛠️ Technologies Used

- Java 11+
- Spring Boot
- Apache Camel
- Apache Kafka
- Maven

## 📁 Project Structure

```
apachecamel/
├── controller/
│   └── MessageProducerController.java
├── processor/
│   └── MetadataProcessor.java
├── routers/
│   └── KafkaConsumerRoute.java
└── ApachecamelApplication.java
```

## 📦 Dependencies

Declared in `pom.xml`, including:
- `spring-boot-starter`
- `camel-spring-boot-starter`
- `camel-kafka`
- `spring-boot-configuration-processor`

## 📡 API Endpoints

### `POST /api/send-direct`

**Description**: Sends a message directly to the configured Kafka topic.

**Request Parameters**:
- `message` (String): The message content to send.

**Example**:
```
POST http://localhost:8080/api/send-direct?message=HelloKafka
```

**Response**:
```json
"Message sent directly to Kafka topic: <your-topic-name>"
```

## 🔄 Camel Route

### Kafka Consumer Route

Defined in `KafkaConsumerRoute.java`:

- Listens to messages from the Kafka topic (configured via application.properties)
- Passes the incoming message to `MetadataProcessor.java` for transformation or logging
- Logs final message to the console or another configured endpoint

## 🧠 Processor

### MetadataProcessor.java

This Camel processor handles the incoming Kafka message:

- Logs metadata
- Optionally enriches or modifies the message
- Can be extended for further business logic

## ⚙️ Configuration

All necessary configurations should be placed in `application.properties`.

Example:
```properties
server.port=8080

# Kafka Configuration
kafka.topic.name=demo-topic
kafka.bootstrap.servers=localhost:9092
```

## ▶️ How to Run

1. Make sure Kafka is running locally or on a configured server.
2. Update the `application.properties` with your Kafka setup.
3. Build and run the project:
```bash
./mvnw clean install
./mvnw spring-boot:run
```
