package com.main.services;

import com.main.entities.account.BalanceEntity;
import com.main.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public BalanceEntity getBalance(String login) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("login", login);
            return jdbcTemplate.queryForObject(
                    """
                    SELECT user_login, account_balance FROM accounts
                        INNER JOIN users AS a
                        ON accounts.account_id = a.user_id
                    WHERE a.user_login = :login;""",
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return new BalanceEntity(
                                rs.getString("user_login"),
                                rs.getBigDecimal("account_balance")
                        );
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
