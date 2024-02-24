package org.launchcode.professionalprocrastinators.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//This is a bean to help make sure restTemplate is configured correctly, and since multiple services use it, it was recommended in my research to set it up this way
    @Configuration
    public class AppConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }


