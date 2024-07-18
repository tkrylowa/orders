package com.springlessons.orders.controller;

import com.springlessons.orders.model.Order;
import com.springlessons.orders.service.OrderToCatalogService;
import org.springframework.stereotype.Component;
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
        Integer userId = Integer.parseInt(request.pathVariable("user_id"));
        return ServerResponse.ok()
                .body(service.archivePictures(), Order.class);
    }
}
