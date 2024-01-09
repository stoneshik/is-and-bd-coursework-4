package com.main.services;

import com.main.entities.UserEntity;
import com.main.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findByLogin(String login) {
        return jdbcTemplate.queryForObject(
                "select * from users where user_login = ?",
                (rs, rowNum) -> {
                    return new UserEntity(
                            rs.getLong("user_id"),
                            rs.getString("user_email"),
                            rs.getString("user_login"),
                            rs.getString("user_password_hash"),
                            rs.getDate("user_created_datetime"),
                            rs.getString("user_status")
                    );
                },
                login
        );
    }
}
