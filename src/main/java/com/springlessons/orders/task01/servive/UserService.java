package com.springlessons.orders.task01.servive;

import com.springlessons.orders.task01.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final WebClient webClientUser;

  @Autowired
  public UserService(WebClient.Builder webClientBuilder) {
    this.webClientUser = webClientBuilder
        .baseUrl("http://127.0.0.1:8081")
        .build();
  }

  public Mono<User> getUser(Integer userId) {
    return webClientUser.get()
        .uri("/api/v1/user/{id}", userId)
        .retrieve()
        .bodyToMono(User.class);
  }
}