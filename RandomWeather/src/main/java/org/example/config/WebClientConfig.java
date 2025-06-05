package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //================================================================================================================//
    @Bean(name = "webClientProjecteol")
    public WebClient webClientProjecteol() {
        return WebClient.builder()
                .baseUrl("https://projecteol.ru")
                .build();
    }
    @Bean(name = "webClientYandexMaps")
    public WebClient webClientYandexMaps() {
        return WebClient.builder()
                .baseUrl("https://geocode-maps.yandex.ru")
                .build();
    }
}
