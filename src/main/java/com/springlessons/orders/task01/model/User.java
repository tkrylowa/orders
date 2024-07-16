package com.springlessons.orders.task01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
  private Long id;
  private String f_name;
  private String l_name;
  private String email;
}