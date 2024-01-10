package com.main.entities.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderWithAddress {
    private Long orderId;
    private Long accountId;
    private String orderAddress;
    private BigDecimal orderAmount;
    private Date orderDatetime;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private Long orderNum;

    public OrderWithAddress(
            Long orderId,
            Long accountId,
            String orderAddress,
            BigDecimal orderAmount,
            Date orderDatetime,
            String orderType,
            String orderStatus,
            Long orderNum) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.orderAddress = orderAddress;
        this.orderAmount = orderAmount;
        this.orderDatetime = orderDatetime;
        this.orderType = OrderType.getValueByName(orderType);
        this.orderStatus = OrderStatus.getValueByName(orderStatus);
        this.orderNum = orderNum;
    }
}
