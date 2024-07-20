package com.springlessons.orders.task01.servive;

import com.springlessons.orders.task01.model.PaymentInfoDto;
import com.springlessons.orders.task01.model.Product;
import com.springlessons.orders.task01.model.Trader;
import com.springlessons.orders.task01.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentInfoDtoService {

  private final ProductService productService;
  private final TraderService traderService;
  private final UserService userService;

  @Autowired
  public PaymentInfoDtoService(WebClient.Builder webClientBuilder, ProductService productService,
      TraderService traderService, UserService userService) {
    this.productService = productService;
    this.traderService = traderService;
    this.userService = userService;
  }

  public Mono<PaymentInfoDto> getProductInfo(Integer productId, Integer userId, Integer traderId) {
    return Mono.zip(productService.getProduct(productId), userService.getUser(userId),
            traderService.getTrader(traderId))
        .flatMap(objects -> {
          Product product = objects.getT1();
          User user = objects.getT2();
          Trader trader = objects.getT3();
          return Mono.just(new PaymentInfoDto(trader, user, product));
        });
  }
}