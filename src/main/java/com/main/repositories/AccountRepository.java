package com.main.repositories;

import com.main.entities.account.BalanceEntity;

public interface AccountRepository {
    BalanceEntity getBalance(String login);
}
