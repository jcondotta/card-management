package com.blitzar.cards;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

@Testcontainers
public class TestContainer {

    private static final KafkaContainer KAFKA_CONTAINER;
    private static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = (MySQLContainer) new MySQLContainer("mysql:8.0")
                .withDatabaseName("integration-test-db")
                .withLabel("mysql-testcontainer-app", "cards")
                .withReuse(true);

        KAFKA_CONTAINER = new KafkaContainer().withReuse(true);
    }

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        Startables.deepStart(MY_SQL_CONTAINER, KAFKA_CONTAINER).join();

        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", MY_SQL_CONTAINER::getDriverClassName);

        registry.add("spring.kafka.properties.bootstrap.servers", KAFKA_CONTAINER::getBootstrapServers);
        registry.add("spring.kafka.consumer.properties.auto.offset.reset", () -> "earliest");
    }
}


