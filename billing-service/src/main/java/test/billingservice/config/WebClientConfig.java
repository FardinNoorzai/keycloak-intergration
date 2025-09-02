package test.billingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient ordersWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081") // Order Service base URL
                .build();
    }
}
