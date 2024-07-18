package com.springlessons.orders.repository;

import com.springlessons.orders.model.Order;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderRepository extends ReactiveMongoRepository<Order, UUID> {
    // Order o = new Order();
    // o.setPrice(5000);
    // Example.of(o);

    // Flux<Order> findByUserId(int userId);

    // ? указывает на параметр, 0 - индекс парамера
    // fields - поля, по которым будут извлекаться данные
    @Query(value = "{userId :  ?0, price: ?1 }",
            fields = "{picturesIds: 1, orderedAt:  1}")
    Flux<Order> orderWithUserId(int userId, int price);
    // sort({userId: -1})
    // find({userId: {$gt: 100, $lte: 300},
    // price: {$in: [3, 78, 12]}})
    // find({userId: {$in: [3, 78, 12]}})
    // fields = {picturesIds: {$slice: 5}}

    Mono<Order> findById(UUID id);
    // Mono<Order> getById(UUID id);

    Flux<Order> findAllById(UUID id);

}

//
