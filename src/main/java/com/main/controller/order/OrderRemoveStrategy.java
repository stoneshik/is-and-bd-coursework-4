package com.main.controller.order;

import com.main.entities.account.BalanceEntity;
import com.main.entities.order.OrderEntity;

public interface OrderRemoveStrategy {
    boolean remove(OrderEntity orderEntity, BalanceEntity balanceEntity);
}
