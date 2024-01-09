package com.main.repositories;

import com.main.entities.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity findByLogin(String login);
}
