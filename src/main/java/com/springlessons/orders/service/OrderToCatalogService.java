package com.springlessons.orders.service;

import com.springlessons.orders.model.Item;
import com.springlessons.orders.model.Order;
import com.springlessons.orders.model.OrderDto;
import com.springlessons.orders.repository.OrderRepository;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class OrderToCatalogService {

    private final WebClient webClient;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderToCatalogService(WebClient.Builder webClientBuilder, OrderRepository orderRepository) {
        this.webClient = webClientBuilder
                .baseUrl("http://127.0.0.1:8081")
                .build();
        this.orderRepository = orderRepository;
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
                .doOnError(throwable -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400));
                })
                .onErrorReturn(false);
    }

    public Mono<Order> postOrder(Mono<OrderDto> orderDto) {
        return webClient.post()
                .uri("/order")
                .retrieve()
                .toEntity(Order.class)
                .flatMap(voidResponseEntity -> {
                    if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.error(new Throwable());
                    }
                    Order order = new Order();
                    OrderDto dto = orderDto.block();
                    assert dto != null;
                    order.setProducts(dto.getProducts());
                    order.setUserId(dto.getUserId());
                    order.setStatus("new");
                    order.setTotalCost(dto.getProducts().stream()
                            .collect(Collectors.summarizingDouble(Item::getProductCost)).getSum());
                    return orderRepository.save(order);
                })
                .timeout(Duration.ofSeconds(5))
                .doOnError(throwable -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400));
                });
    }

    public Mono<List<Order>> getByProductId(Integer productId) {
        return webClient.post()
                .uri("order/{productId}", productId)
                .retrieve()
                .toEntity(Order.class)
                .flatMap(voidResponseEntity -> {
                    if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.error(new Throwable());
                    }
                    return orderRepository.findAllByProductId(productId);
                })
                .timeout(Duration.ofSeconds(5))
                .doOnError(throwable -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400));
                });
    }

    public Mono<List<Order>> getByUserIdFilteredByPrice(int userId, double price) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("order/{userId}/filter")
                        .queryParam("price", price)
                        .build(userId))
                .retrieve()
                .toEntity(Order.class)
                .flatMap(voidResponseEntity -> {
                    if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.error(new Throwable());
                    }
                    return orderRepository.findAllByUserIdAndPrice(userId, price);
                })
                .timeout(Duration.ofSeconds(5))
                .doOnError(throwable -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400));
                });
    }
}