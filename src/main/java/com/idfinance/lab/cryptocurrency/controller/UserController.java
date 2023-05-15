package com.idfinance.lab.cryptocurrency.controller;

import com.idfinance.lab.cryptocurrency.dto.UserCreateDto;
import com.idfinance.lab.cryptocurrency.dto.UserDto;
import com.idfinance.lab.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto register(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.registerUser(userCreateDto);
    }
}
