package com.springlessons.orders.task01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trader {
    private Long id;
    private String name;
    private String inn;
    private String phone;
}