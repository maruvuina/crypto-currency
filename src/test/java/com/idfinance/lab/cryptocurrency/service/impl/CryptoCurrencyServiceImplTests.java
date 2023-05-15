package com.idfinance.lab.cryptocurrency.service.impl;

import com.idfinance.lab.cryptocurrency.client.CryptoCurrencyApiClient;
import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyApiResponse;
import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;
import com.idfinance.lab.cryptocurrency.mapper.CryptoCurrencyMapper;
import com.idfinance.lab.cryptocurrency.model.CryptoCurrency;
import com.idfinance.lab.cryptocurrency.model.User;
import com.idfinance.lab.cryptocurrency.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyServiceImplTests {

    @Mock
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Mock
    private CryptoCurrencyMapper cryptoCurrencyMapper;

    @Mock
    private CryptoCurrencyApiClient cryptoCurrencyApiClient;

    @InjectMocks
    private CryptoCurrencyServiceImpl cryptoCurrencyService;


    private final String symbol = "BTC";

    private final CryptoCurrency cryptoCurrency = new CryptoCurrency();

    private final CryptoCurrencyDto cryptoCurrencyDto = new CryptoCurrencyDto();

    @Test
    void testGetAll() {
        // given
        List<CryptoCurrency> cryptoCurrencies = Collections.singletonList(cryptoCurrency);
        List<CryptoCurrencyDto> expected = Collections.singletonList(cryptoCurrencyDto);

        when(cryptoCurrencyRepository.findAll()).thenReturn(cryptoCurrencies);
        when(cryptoCurrencyMapper.mapToDto(cryptoCurrency)).thenReturn(cryptoCurrencyDto);

        // when
        List<CryptoCurrencyDto> actual = cryptoCurrencyService.getAll();

        // then
        assertEquals(expected, actual);
        verify(cryptoCurrencyRepository).findAll();
        verify(cryptoCurrencyMapper).mapToDto(cryptoCurrency);
    }

    @Test
    void testGetPrice() {
        // given
        BigDecimal expected = new BigDecimal("100");
        cryptoCurrency.setActualPrice(expected);
        cryptoCurrencyDto.setActualPrice(expected);
        when(cryptoCurrencyRepository.findBySymbol(symbol)).thenReturn(cryptoCurrency);
        when(cryptoCurrencyMapper.mapToDto(cryptoCurrency)).thenReturn(cryptoCurrencyDto);

        // when
        BigDecimal actual = cryptoCurrencyService.getPrice(symbol);

        // then
        assertEquals(expected, actual);
        verify(cryptoCurrencyRepository).findBySymbol(symbol);
    }

    @Test
    void testGetBySymbol() {
        // given
        when(cryptoCurrencyRepository.findBySymbol(symbol)).thenReturn(cryptoCurrency);
        when(cryptoCurrencyMapper.mapToDto(cryptoCurrency)).thenReturn(cryptoCurrencyDto);

        // when
        CryptoCurrencyDto actual = cryptoCurrencyService.getBySymbol(symbol);

        // then
        assertEquals(cryptoCurrencyDto, actual);
        verify(cryptoCurrencyRepository).findBySymbol(symbol);
        verify(cryptoCurrencyMapper).mapToDto(cryptoCurrency);
    }

    @Test
    void testNotifyAboutPriceChange() {
        // given
        CryptoCurrency cryptoCurrency = CryptoCurrency.builder()
                .id(1L)
                .symbol(symbol)
                .actualPrice(new BigDecimal("110"))
                .build();
        BigDecimal oldPrice = new BigDecimal("100");
        BigDecimal newPrice = new BigDecimal("110");
        User user1 = User.builder()
                .id(1L)
                .username("harry")
                .cryptoCurrency(cryptoCurrency)
                .userPrice(oldPrice)
                .build();
        User user2 = User.builder()
                .id(1L)
                .username("ron")
                .cryptoCurrency(cryptoCurrency)
                .userPrice(oldPrice)
                .build();
        cryptoCurrency.setUsers(Arrays.asList(user1, user2));

        List<CryptoCurrency> oldCryptoCurrencyPrices = Collections.singletonList(cryptoCurrency);
        List<CryptoCurrency> expectedCryptoCurrencyPrices = Collections.singletonList(cryptoCurrency);

        CryptoCurrencyApiResponse cryptoCurrencyApiResponse = new CryptoCurrencyApiResponse();
        cryptoCurrencyApiResponse.setSymbol(symbol);
        cryptoCurrencyApiResponse.setPriceUsd(newPrice);

        when(cryptoCurrencyRepository.findAll()).thenReturn(oldCryptoCurrencyPrices);
        when(cryptoCurrencyApiClient.getCryptoCurrencyById(cryptoCurrency.getId())).thenReturn(cryptoCurrencyApiResponse);
        when(cryptoCurrencyMapper.mapFromCryptoCurrencyApiResponse(cryptoCurrencyApiResponse)).thenReturn(cryptoCurrency);
        when(cryptoCurrencyRepository.saveAll(expectedCryptoCurrencyPrices)).thenReturn(expectedCryptoCurrencyPrices);

        // when
        cryptoCurrencyService.notifyAboutPriceChange();

        // then
        verify(cryptoCurrencyRepository).findAll();
        verify(cryptoCurrencyApiClient).getCryptoCurrencyById(cryptoCurrency.getId());
        verify(cryptoCurrencyMapper).mapFromCryptoCurrencyApiResponse(cryptoCurrencyApiResponse);
        verify(cryptoCurrencyRepository).saveAll(expectedCryptoCurrencyPrices);
    }
}