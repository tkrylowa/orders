package com.springlessons.orders.task01.servive;

import com.springlessons.orders.task01.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  private final WebClient webClientProduct;

  @Autowired
  public ProductService(WebClient.Builder webClientBuilder) {
    this.webClientProduct = webClientBuilder
        .baseUrl("http://127.0.0.1:8082")
        .build();
  }

  public Mono<Product> getProduct(Integer productId) {
    return webClientProduct.get()
        .uri("/api/v1/catalog/product/{id}", productId)
        .retrieve()
        .bodyToMono(Product.class);
  }
}