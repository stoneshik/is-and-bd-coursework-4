package com.main.services;

import com.main.entities.order.OrderWithAddress;
import com.main.repositories.OrderWithAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderWithAddressService implements OrderWithAddressRepository {
    private final JdbcTemplate jdbcTemplate;


    private Long getAccountId(String login) {
        try {
            return jdbcTemplate.queryForObject(
                    """
                    SELECT accounts.account_id FROM accounts
                        INNER JOIN users AS a
                        ON accounts.account_id = a.user_id
                    WHERE a.user_login = ?;""",
                    (rs, rowNum) -> {
                        return rs.getLong("account_id");
                    },
                    login
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<OrderWithAddress> getPaidOrders(String login) {
        final Long accountId = getAccountId(login);
        if (accountId == null) {
            return null;
        }
        try {
            return jdbcTemplate.query(
                    """
                    SELECT order_id,
                           account_id,
                           a.vending_point_address AS order_address,
                           order_amount,
                           order_datetime,
                           order_type,
                           order_status,
                           order_num FROM orders
                        INNER JOIN vending_points AS a
                        ON a.vending_point_id = orders.vending_point_id
                    WHERE orders.account_id = ? AND orders.order_status = 'paid';""",
                    (rs, rowNum) -> {
                        return new OrderWithAddress(
                            rs.getLong("order_id"),
                            rs.getLong("account_id"),
                            rs.getString("order_address"),
                            rs.getBigDecimal("order_amount"),
                            rs.getDate("order_datetime"),
                            rs.getString("order_type"),
                            rs.getString("order_status"),
                            rs.getLong("order_num")
                        );
                    },
                    accountId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
