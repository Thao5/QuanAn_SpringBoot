package com.thao.quanan;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan("com.thao.pojo")
@EnableJpaRepositories("com.thao.repository")
@ComponentScan({
    "com.thao.service",
    "com.thao.Controllers",})
@SpringBootApplication
public class QuananApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuananApplication.class, args);
    }

}
