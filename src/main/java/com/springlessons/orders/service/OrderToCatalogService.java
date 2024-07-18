package com.springlessons.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
                .baseUrl("http://127.0.0.1:8081")
                .build();
    }

    public Mono<Boolean> archivePictures(List<Integer> picturesIds) {

        String ids = String.join(",", picturesIds.stream().map(String::valueOf).toList());
        return webClient.get() // http method
                .uri("/pictures/archive/{ids}", ids) // 2,8,9
                .retrieve()
//                .bodyToMono(Cat.class)
//                .bodyToFlux(Cat.class)
//                .bodyToMono(new ParameterizedTypeReference<List<Cat>>() {
//                })
//                .bodyToFlux(new ParameterizedTypeReference<List<Cat>>() {
//                })
                .toEntity(Boolean.class)
                .flatMap(voidResponseEntity -> {
                    if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.error(new Throwable());

                    }
                    return Mono.just(true);
                })
                .timeout(Duration.ofSeconds(5)) // TimeOutException

                // .doOnError(throwable -> {
                //    throw new ResponseStatusException();
                // })
                // .onErrorReturn(false);
    }
}
