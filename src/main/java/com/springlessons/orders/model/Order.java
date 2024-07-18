package com.springlessons.orders.model;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.UUID;

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


    public Order() {
    }

    public Order(UUID id, int userId) {
        this.id = id;
        this.userId = userId;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



}
