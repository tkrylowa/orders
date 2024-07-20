package com.springlessons.orders.service;

import com.springlessons.orders.model.CatalogPriceDto;
import com.springlessons.orders.model.Order;
import com.springlessons.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderToCatalogService {
    private final WebClient webClient;
    private final CatalogPriceService catalogPriceService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderToCatalogService(WebClient.Builder webClientBuilder, CatalogPriceService catalogPriceService, OrderRepository orderRepository) {
        this.webClient = webClientBuilder
                .baseUrl("http://127.0.0.1:8081")
                .build();
        this.catalogPriceService = catalogPriceService;
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
                .timeout(Duration.ofSeconds(5)); // TimeOutException

        // .doOnError(throwable -> {
        //    throw new ResponseStatusException();
        // })
        // .onErrorReturn(false);
    }

    public Mono<Order> makeOrder(Map<Integer, Integer> products) {
        Mono<List<CatalogPriceDto>> catalogPrice = catalogPriceService.getProductsFromCatalog(products.keySet());
        return webClient.post()
                .uri("/order")
                .retrieve()
                .toEntity(Order.class)
                .flatMap(voidResponseEntity -> {
                    if (!voidResponseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.error(new Throwable());
                    }
                    Order order = new Order();
                    order.setProducts(products);
                    order.setPrice(Objects.requireNonNull(catalogPrice.block()).stream()
                            .collect(Collectors.summarizingDouble(CatalogPriceDto::getPrice)).getSum());
                    return orderRepository.save(order);
                })
                .timeout(Duration.ofSeconds(5))
                .doOnError(throwable -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400));
                });
    }
}
