package com.main.controller.order;

import com.main.entities.account.BalanceEntity;
import com.main.entities.order.OrderEntity;
import com.main.services.AccountService;
import com.main.services.OrderService;

import java.math.BigDecimal;

public class OrderPaidRemoveStrategy implements OrderRemoveStrategy {
    private final OrderService orderService;
    private final AccountService accountService;

    public OrderPaidRemoveStrategy(OrderService orderService, AccountService accountService) {
        this.orderService = orderService;
        this.accountService = accountService;
    }

    @Override
    public boolean remove(OrderEntity orderEntity, BalanceEntity balanceEntity) {
        final BigDecimal newAccountBalance = balanceEntity.getAccountBalance().add(
                orderEntity.getOrderAmount()
        );
        final boolean isAccountBalanceUpdate = accountService.updateBalance(
                balanceEntity.getAccountId(), newAccountBalance
        );
        if (!isAccountBalanceUpdate) {
            return false;
        }
        return orderService.removeById(orderEntity.getOrderId());
    }
}
