package com.springlessons.orders.task01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentInfoDto {

  private Trader trader;
  private User user;
  private Product product;
}