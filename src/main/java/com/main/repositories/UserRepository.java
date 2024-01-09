package com.main.repositories;

import com.main.entities.UserEntity;

import java.util.List;

public interface UserRepository {
    List<UserEntity> findByLogin(String login);
}
