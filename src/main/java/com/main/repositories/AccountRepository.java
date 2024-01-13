package com.main.repositories;

import com.main.entities.account.BalanceEntity;

import java.math.BigDecimal;

public interface AccountRepository {
    BalanceEntity getBalance(String login);

    boolean updateBalance(Long accountId, BigDecimal newAccountBalance);
}
