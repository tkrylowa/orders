package com.springlessons.orders.controller;

import com.springlessons.orders.Cat;
import com.springlessons.orders.model.Item;
import com.springlessons.orders.model.Order;
import com.springlessons.orders.model.OrderDto;
import com.springlessons.orders.service.OrderToCatalogService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderHandler {

  private OrderToCatalogService service;

  public OrderHandler(OrderToCatalogService service) {
    this.service = service;
  }

  public Mono<ServerResponse> getByUserId(ServerRequest request) {
    Integer userId = Integer.parseInt(request.pathVariable("userId"));
    return ServerResponse.ok()
        .body(service.archivePictures(), Order.class);
  }

  public Mono<ServerResponse> getByProductId(ServerRequest request) {
    Integer productId = Integer.parseInt(request.pathVariable("productId"));
    return ServerResponse.ok()
        .body(service.getByProductId(productId), Order.class);
  }

  public Mono<ServerResponse> getByUserIdAndPrice(ServerRequest request) {
    Integer userId = Integer.parseInt(request.pathVariable("userId"));
    Double price = Double.parseDouble(request.pathVariable("price"));
    return ServerResponse.ok()
        .body(service.getByUserIdFilteredByPrice(userId, price),
            new ParameterizedTypeReference<List<Order>>() {
            });
  }

  public Mono<ServerResponse> postNewOrder(ServerRequest request) {
      Mono<OrderDto> orderDto = request.body(BodyExtractors.toMono(OrderDto.class));
    return ServerResponse.ok()
        .body(service.postOrder(orderDto), Order.class);
  }


}