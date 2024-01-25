package com.main.controller.order.remove;

import com.main.controller.order.remove.OrderRemoveStrategy;
import com.main.entities.account.BalanceEntity;
import com.main.entities.order.OrderEntity;
import com.main.services.OrderService;

public class OrderNotPaidRemoveStrategy implements OrderRemoveStrategy {
    private final OrderService orderService;

    public OrderNotPaidRemoveStrategy(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean remove(OrderEntity orderEntity, BalanceEntity balanceEntity) {
        return orderService.removeById(orderEntity.getOrderId());
    }
}
