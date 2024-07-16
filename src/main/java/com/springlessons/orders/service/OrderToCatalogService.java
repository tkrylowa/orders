package com.springlessons.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class OrderToCatalogService {
    private final WebClient webClient;

    @Autowired
    public OrderToCatalogService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://127.0.0.1:8081/catalog/api")
                .build();
    }

    public Mono<Boolean> archivePictures(List<Integer> picturesIds) {
        String ids = String.join(",", picturesIds.stream().map(String::valueOf).toList());
        return webClient.get() // http method
                .uri("/pictures/archive/{ids}", ids) // 2,8,9
                .retrieve()
                .toEntity(Void.class)
                .log()
                .flatMap(voidResponseEntity -> {
                    if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(false);
                    }
                    return Mono.just(true);
                })
                .timeout(Duration.ofSeconds(5)) // TimeOutException
                .onErrorReturn(false);
    }
}
