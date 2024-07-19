package com.springlessons.orders.model;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("orders")
public class Order {

  // уникальный идентификатор записи
  @MongoId
  private UUID id;
  // - похожи на индексы в sql
  // - могут быть составными (но не по всем полям)
  // - хранятся отдельно в упорядоченном виде
  // - не могут создавать связей с другими документами
  // - замедляют вставку данных
  @Indexed
  private int userId;
  private List<Item> products;
  private Double totalCost;
  private String status;

  public Order(int userId, List<Item> items) {
    this.userId = userId;
    this.products = items;
  }
}