package com.main.services;

import com.main.entities.order.OrderWithAddress;
import com.main.repositories.OrderWithAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderWithAddressService implements OrderWithAddressRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;


    private Long getAccountId(String login) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("login", login);
            return jdbcTemplate.queryForObject(
                    """
                    SELECT accounts.account_id FROM accounts
                        INNER JOIN users AS a
                        ON accounts.account_id = a.user_id
                    WHERE a.user_login = :login;""",
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return rs.getLong("account_id");
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private List<OrderWithAddress> getOrdersByLoginAndStatus(String login, String orderStatus) {
        final Long accountId = getAccountId(login);
        if (accountId == null) {
            return null;
        }
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("account_id", accountId);
            mapSqlParameterSource.addValue("order_status", orderStatus);
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
                    WHERE orders.account_id = :account_id AND orders.order_status = :order_status::order_status_enum;""",
                    mapSqlParameterSource,
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
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<OrderWithAddress> getPaidOrders(String login) {
        return getOrdersByLoginAndStatus(login, "paid");
    }

    @Override
    public List<OrderWithAddress> getNotPaidOrders(String login) {
        return getOrdersByLoginAndStatus(login, "not_paid");
    }
}
