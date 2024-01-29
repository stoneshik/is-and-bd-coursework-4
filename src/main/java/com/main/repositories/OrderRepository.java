package com.main.repositories;

import com.main.entities.order.OrderEntity;

import java.math.BigDecimal;

public interface OrderRepository {
    OrderEntity getById(Long accountId, Long orderId);
    boolean removeById(Long orderId);

    boolean createNewScanOrder(Long accountId, Long vendingPointId, BigDecimal orderAmount);
}
