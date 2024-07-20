package com.springlessons.orders.service;

import com.springlessons.orders.model.CatalogPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@Service
public class CatalogPriceService {
    private final WebClient webClientCatalogPrice;

    @Autowired
    public CatalogPriceService(WebClient.Builder webClientBuilder) {
        this.webClientCatalogPrice = webClientBuilder
                .baseUrl("http://127.0.0.1:8085")
                .build();
    }

    Mono<List<CatalogPriceDto>> getProductsFromCatalog(Set<Integer> products) {
        String ids = String.join(",", products.stream().map(String::valueOf).toList());
        URI uri;
        try {
            uri = new URI(String.format("/api/v1/catalog/ids=%s", ids));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return webClientCatalogPrice.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
