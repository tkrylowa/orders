package com.springlessons.orders;

import com.springlessons.orders.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class ControllerServiceRepositoryExample {
}


interface ExampleRepository extends ReactiveMongoRepository<Order, UUID> {}

@Service
class ExampleService {
    private ExampleRepository exampleRepository;

    public ExampleService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    public Mono<Order> findById(UUID id) {
        return exampleRepository.findById(id);
    }
}

@RestController
class ExampleController{
    private ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/{id}")
    public Mono<Order> findById(UUID id) {
        return exampleService.findById(id);
    }
}