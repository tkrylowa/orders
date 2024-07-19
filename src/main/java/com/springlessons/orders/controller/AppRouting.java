package com.springlessons.orders.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AppRouting {

  @Bean
  public RouterFunction<ServerResponse> routes(OrderHandler orderHandler) {
    return RouterFunctions.route()
        .GET("/api/v1/order/{user_id}",
            request -> orderHandler.getByUserId(request))
        .GET("order/{productID}", request -> orderHandler.getByProductId(request))
        .GET("order/{userId}/filter", request -> orderHandler.getByUserIdAndPrice(request))
        .POST("/order", request -> orderHandler.postNewOrder(request))
        .build();
  }
}