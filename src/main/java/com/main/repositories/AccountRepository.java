package com.main.repositories;

import com.main.entities.account.BalanceEntity;
import com.main.entities.replenish.ReplenishEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {
    Long getAccountId(String login);
    BalanceEntity getBalance(String login);
    boolean updateBalance(Long accountId, BigDecimal newAccountBalance);
}
