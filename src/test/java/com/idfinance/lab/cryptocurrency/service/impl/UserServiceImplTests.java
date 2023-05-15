package com.idfinance.lab.cryptocurrency.service.impl;

import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;
import com.idfinance.lab.cryptocurrency.dto.UserCreateDto;
import com.idfinance.lab.cryptocurrency.dto.UserDto;
import com.idfinance.lab.cryptocurrency.exception.CryptoCurrencyNotFoundException;
import com.idfinance.lab.cryptocurrency.exception.UsernameAlreadyExistsException;
import com.idfinance.lab.cryptocurrency.mapper.CryptoCurrencyMapper;
import com.idfinance.lab.cryptocurrency.mapper.UserMapper;
import com.idfinance.lab.cryptocurrency.model.CryptoCurrency;
import com.idfinance.lab.cryptocurrency.model.User;
import com.idfinance.lab.cryptocurrency.repository.UserRepository;
import com.idfinance.lab.cryptocurrency.service.CryptoCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CryptoCurrencyService cryptoCurrencyService;

    @Mock
    private CryptoCurrencyMapper cryptoCurrencyMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private final String username = "harryPotter";

    private String symbol = "BTC";

    private UserCreateDto userCreateDto;

    @BeforeEach
    void setUp() {
        userCreateDto = new UserCreateDto(username, symbol);
    }

    @Test
    void testRegisterUser() {
        // given
        CryptoCurrency cryptoCurrency = CryptoCurrency.builder()
                .id(80L)
                .symbol(symbol)
                .actualPrice(new BigDecimal("1.0"))
                .build();
        User user = User.builder()
                .id(1L)
                .username(username)
                .cryptoCurrency(cryptoCurrency)
                .userPrice(cryptoCurrency.getActualPrice())
                .build();
        CryptoCurrencyDto cryptoCurrencyDto = CryptoCurrencyDto.builder()
                .id(80L)
                .symbol(symbol)
                .actualPrice(cryptoCurrency.getActualPrice())
                .build();
        UserDto expectedUserDto = UserDto.builder()
                .id(1L)
                .username(username)
                .userPrice(cryptoCurrency.getActualPrice())
                .build();

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(cryptoCurrencyService.getBySymbol(symbol)).thenReturn(cryptoCurrencyDto);
        when(cryptoCurrencyMapper.mapFromDto(cryptoCurrencyDto)).thenReturn(cryptoCurrency);
        when(userMapper.mapFromDto(userCreateDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToDto(user)).thenReturn(expectedUserDto);

        // when
        UserDto actual = userService.registerUser(userCreateDto);

        // then
        assertEquals(expectedUserDto, actual);

        verify(userRepository).existsByUsername(username);
        verify(cryptoCurrencyService).getBySymbol(symbol);
        verify(cryptoCurrencyMapper).mapFromDto(cryptoCurrencyDto);
        verify(userMapper).mapFromDto(userCreateDto);
        verify(userRepository).save(user);
        verify(userMapper).mapToDto(user);
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        // given
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // when
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registerUser(userCreateDto));

        // then
        verify(userRepository).existsByUsername(username);
        verify(cryptoCurrencyService, never()).getBySymbol(any());
        verify(cryptoCurrencyMapper, never()).mapFromDto(any());
        verify(userMapper, never()).mapFromDto(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).mapToDto(any());
    }

    @Test
    void testRegisterUserWithNonExistingSymbol() {
        // given
        symbol = "notExists";
        userCreateDto.setSymbol(symbol);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(cryptoCurrencyService.getBySymbol(symbol)).thenThrow(new CryptoCurrencyNotFoundException("Symbol not found"));

        // when
        assertThrows(CryptoCurrencyNotFoundException.class, () -> userService.registerUser(userCreateDto));

        // then
        verify(userRepository).existsByUsername(username);
        verify(cryptoCurrencyService).getBySymbol(symbol);
        verify(cryptoCurrencyMapper, never()).mapFromDto(any());
        verify(userMapper, never()).mapFromDto(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).mapToDto(any());
    }
}