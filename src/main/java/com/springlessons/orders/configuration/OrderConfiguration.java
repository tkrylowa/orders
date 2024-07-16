package com.springlessons.orders.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * Настройки для текущего сервиса. <br/>
 * Бины, которые будут доступны из контекста:
 * <ol>
 *     <li>WebClient.Builder webClientBuilder</li>
 * </ol>
 */
@Configuration
public class OrderConfiguration {
    /**
     * Бин позволит создавать клиентов. <br/>
     * Все клиенты, созданные данным Builder будут обладать общими настройками:
     * <ol>
     *     <li>время ожидания для все запросов - 5 секунд, после чего сработает TimeOutException</li>
     * </ol>
     *
     * @return строитель, создающий WebClient для отправки http запросов
     * @see <a href="URL#https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.Builder.html">Документация</a>
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

}
