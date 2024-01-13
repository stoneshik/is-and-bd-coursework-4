package com.main.repositories;

import com.main.entities.order.OrderEntity;

public interface OrderRepository {
    OrderEntity getById(Long accountId, Long orderId);
    boolean removeById(Long orderId);
}
