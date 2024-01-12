package com.main.entities.account;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BalanceEntity {
    private final String userLogin;
    private final BigDecimal accountBalance;

    public BalanceEntity(String userLogin, BigDecimal accountBalance) {
        this.userLogin = userLogin;
        this.accountBalance = accountBalance;
    }
}
