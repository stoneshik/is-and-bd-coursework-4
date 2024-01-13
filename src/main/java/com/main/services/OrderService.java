package com.main.services;

import com.main.entities.order.OrderEntity;
import com.main.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public OrderEntity getById(Long accountId, Long orderId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("account_id", accountId);
            mapSqlParameterSource.addValue("order_id", orderId);
            return jdbcTemplate.queryForObject(
                    """
                    SELECT
                        order_id,
                        account_id,
                        vending_point_id,
                        order_amount,
                        order_datetime,
                        order_type,
                        order_status,
                        order_num FROM orders WHERE order_id = :order_id AND account_id = :account_id;""",
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return new OrderEntity(
                                rs.getLong("order_id"),
                                rs.getLong("account_id"),
                                rs.getLong("vending_point_id"),
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
    public boolean removeById(Long orderId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            int queryResult = jdbcTemplate.update(
                    "DELETE FROM orders WHERE order_id = :order_id;",
                    mapSqlParameterSource
            );
            return queryResult > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
