package kr.pe.junho85.demo;

import kr.pe.junho85.demo.model.TestMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @KafkaListener(id = "test", topics = "test")
    public void testListener(TestMessage testMessage) {
        System.out.println(testMessage);
    }
}
