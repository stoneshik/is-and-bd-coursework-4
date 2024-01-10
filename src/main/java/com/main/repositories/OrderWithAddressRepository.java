package com.main.repositories;

import com.main.entities.order.OrderWithAddress;

import java.util.List;

public interface OrderWithAddressRepository {
    List<OrderWithAddress> getPaidOrders(String login);
}
