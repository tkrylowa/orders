package com.springlessons.orders;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

record Cat(int id, String name, String color, int ownerId) { }

record Owner(int id, String name, String phone) { }

public class Producers {

    public Mono<Cat> catMono() {
        return Mono.just(
                new Cat(4, "ashly", "black", 23)
        );
    }

    public Mono<Owner> ownerMono() {
        return Mono.just(
                new Owner(23, "mary", "111111")
        );
    }

    public Flux<Cat> catFlux() {
        return Flux.just(
                new Cat(1, "tom", "black", 21),
                new Cat(2, "jack", "white", 22),
                new Cat(3, "jessy", "ginger", 21)
        );
    }

    public Flux<Owner> ownerFlux() {
        return Flux.just(
                new Owner(21, "mike", "999999"),
                new Owner(22, "luna", "555555"),
                new Owner(23, "mary", "111111")
        );
    }

    public void concatMethods() {
        Mono<Cat> catMono = catMono();
        Flux<Cat> catFlux = catFlux();
        Flux<Owner> ownerFlux = ownerFlux();

        ownerFlux()
                .flatMap(
                    owner -> {

                        return Flux.just();
                    }
        );

        // асинхронный
        Flux.merge(catMono(), catFlux()) // Flux
                .flatMap(cat -> {
                    // логика обработки данных
                    return Flux.empty();
                });
        // последовательный
        Flux.concat(catMono(), catFlux()) // Flux
                .flatMap(cat -> {
                    // логика обработки данных
                    return Flux.empty();
                });

        Mono.zip(catMono(), ownerMono()) // Tuple
                .flatMap(objects -> {
                    Cat cat = objects.getT1(); // cat
                    Owner owner = objects.getT2(); // owner
                    return Mono.empty();
                }); // Tuple

        catMono().zipWith(ownerMono())
                .flatMap(objects -> {
                    Cat cat = objects.getT1(); // cat
                    Owner owner = objects.getT2(); // owner
                    return Mono.empty();
                });
    }

}
