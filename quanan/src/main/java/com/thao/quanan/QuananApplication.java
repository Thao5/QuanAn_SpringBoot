package com.thao.quanan;

import com.thao.configs.JwtSecurityConfig;
import com.thao.configs.SpringSecurityConfig;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableAutoConfiguration(exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
//        )
@EnableTransactionManagement
@EntityScan("com.thao.pojo")
@EnableJpaRepositories("com.thao.repository")
@ComponentScan({
    "com.thao.repository.impl",
    "com.thao.service",
    "com.thao.Controllers",
    "com.thao.validation"})
@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
@Import({SpringSecurityConfig.class, JwtSecurityConfig.class})
public class QuananApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuananApplication.class, args);
    }

}
