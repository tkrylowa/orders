package com.springlessons.orders.controller;

import com.springlessons.orders.model.Order;
import com.springlessons.orders.service.OrderToCatalogService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class OrderHandler {
    private final OrderToCatalogService service;

    public OrderHandler(OrderToCatalogService service) {
        this.service = service;
    }

    public Mono<ServerResponse> getByUserId(ServerRequest request) {
        Integer userId = Integer.parseInt(request.pathVariable("user_id"));
        return ServerResponse.ok()
                .body(service.archivePictures(List.of(userId)), Order.class);
    }

    public Mono<ServerResponse> createNewOrder(ServerRequest request) {
        Mono<Map<Integer, Integer>> products = request.body(BodyExtractors.toMono(new ParameterizedTypeReference<>() {
        }));
        return ServerResponse.ok()
                .body(service.makeOrder(Objects.requireNonNull(products.block())), Order.class);
    }
}
