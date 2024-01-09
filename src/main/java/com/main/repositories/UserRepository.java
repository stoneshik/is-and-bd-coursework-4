package com.main.repositories;

import com.main.entities.UserEntity;


public interface UserRepository {
    int create(String email, String login, String password);
    UserEntity findByLogin(String login);
}
