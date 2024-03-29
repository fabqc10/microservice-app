package com.fabdev.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

// The reason to do this is to have the ability to inject RabbitMQMessageProducer
@SpringBootApplication(
        scanBasePackages = {
                "com.fabdev.customer",
                "com.fabdev.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.fabdev.clients"
)
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class,args);
    }
}