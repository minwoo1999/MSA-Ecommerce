package com.example.userservicegradle.service;

import com.example.userservicegradle.dto.UserDto;
import com.example.userservicegradle.jpa.UserEntity;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(String userId);

    List<UserEntity> getUserByAll();
}
