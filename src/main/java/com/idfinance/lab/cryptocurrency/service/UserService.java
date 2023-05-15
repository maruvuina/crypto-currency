package com.idfinance.lab.cryptocurrency.service;

import com.idfinance.lab.cryptocurrency.dto.UserCreateDto;
import com.idfinance.lab.cryptocurrency.dto.UserDto;

/**
 * The {@link UserService} interface provides operations for managing user.
 */
public interface UserService {

    /**
     * Registers a new user based on the provided {@link UserCreateDto}.
     * @param userCreateDto The {@link UserDto} object containing user registration details.
     * @return The {@link UserDto} representing the registered user.
     */
    UserDto registerUser(UserCreateDto userCreateDto);
}
