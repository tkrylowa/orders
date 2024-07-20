package com.springlessons.orders.task01.servive;

import com.springlessons.orders.task01.model.Trader;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TraderService {

  private final WebClient webClientTrader;

  @Autowired
  public TraderService(WebClient.Builder webClientBuilder) {
    this.webClientTrader = webClientBuilder
        .baseUrl("http://127.0.0.1:8083")
        .build();
  }

  public Mono<Trader> getTrader(Integer traderId) {
    return webClientTrader.get()
        .uri("/api/v1/traders/{id}", traderId)
        .retrieve()
        .toEntity(Trader.class)
        .flatMap(voidResponseEntity -> {
          if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
            return Mono.just(new Trader());
          }
          return Mono.just(Objects.requireNonNull(voidResponseEntity.getBody()));
        });
  }

}