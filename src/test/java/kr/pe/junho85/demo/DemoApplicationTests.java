package kr.pe.junho85.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void publish() {
        // TestMessage.builder().testType(TestType.ALPHA)
        kafkaTemplate.send("test", "{\"testType\": \"ALPHA\"}");
        kafkaTemplate.send("test", "{\"testType\": \"TEST\"}");
    }

}
