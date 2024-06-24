package com.fever.events;

import com.fever.events.infrastructure.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//@SpringBootTest
//@ContextConfiguration(classes = TestConfig.class)
//class EventsApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//}
@SpringBootApplication
public class EventsApplicationTests {

    public static void main(final String[] args) {

        SpringApplication.run(EventsApplicationTests.class, args);

    }

}
