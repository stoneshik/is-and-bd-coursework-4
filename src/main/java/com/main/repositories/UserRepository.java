package com.main.repositories;

import com.main.entities.UserEntity;


public interface UserRepository {
    UserEntity findByLogin(String login);
}
