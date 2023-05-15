package com.idfinance.lab.cryptocurrency.service.impl;

import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;
import com.idfinance.lab.cryptocurrency.dto.UserCreateDto;
import com.idfinance.lab.cryptocurrency.dto.UserDto;
import com.idfinance.lab.cryptocurrency.exception.UsernameAlreadyExistsException;
import com.idfinance.lab.cryptocurrency.mapper.CryptoCurrencyMapper;
import com.idfinance.lab.cryptocurrency.mapper.UserMapper;
import com.idfinance.lab.cryptocurrency.model.CryptoCurrency;
import com.idfinance.lab.cryptocurrency.model.User;
import com.idfinance.lab.cryptocurrency.repository.UserRepository;
import com.idfinance.lab.cryptocurrency.service.CryptoCurrencyService;
import com.idfinance.lab.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final CryptoCurrencyService cryptoCurrencyService;

    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    @Override
    public UserDto registerUser(UserCreateDto userCreateDto) {
        String username = userCreateDto.getUsername();
        if (userRepository.existsByUsername(username)) {
            log.error("Username already exists {}", username);
            throw new UsernameAlreadyExistsException("Username already exists = " + username);
        }
        CryptoCurrencyDto cryptoCurrencyDto = cryptoCurrencyService.getBySymbol(userCreateDto.getSymbol());
        CryptoCurrency cryptoCurrency = cryptoCurrencyMapper.mapFromDto(cryptoCurrencyDto);
        User user = userMapper.mapFromDto(userCreateDto);
        user.setCryptoCurrency(cryptoCurrency);
        user.setUserPrice(cryptoCurrency.getActualPrice());
        return userMapper.mapToDto(userRepository.save(user));
    }
}
