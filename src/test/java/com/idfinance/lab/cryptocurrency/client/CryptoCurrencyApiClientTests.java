package com.idfinance.lab.cryptocurrency.client;

import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyApiResponse;
import com.idfinance.lab.cryptocurrency.exception.CryptoCurrencyNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyApiClientTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CryptoCurrencyApiClient cryptoCurrencyApiClient;

    @Test
    void testGetCryptoCurrencyById() {
        // given
        Long id = 1L;
        CryptoCurrencyApiResponse currencyApiResponse =
                new CryptoCurrencyApiResponse("90", "BTC", new BigDecimal("100.0"));
        CryptoCurrencyApiResponse[] response = { currencyApiResponse };
        when(restTemplate.getForEntity(anyString(), any(Class.class)))
                .thenReturn(new ResponseEntity(response, HttpStatus.OK));

        // when
        CryptoCurrencyApiResponse actual = cryptoCurrencyApiClient.getCryptoCurrencyById(id);

        // then
        assertNotNull(actual);
        assertEquals(currencyApiResponse.getId(), actual.getId());
        assertEquals(currencyApiResponse.getSymbol(), actual.getSymbol());
        assertEquals(currencyApiResponse.getPriceUsd(), actual.getPriceUsd());
    }

    @Test
    void testGetCryptoCurrencyByIdNotFound() {
        // given
        Long id = 1L;
        CryptoCurrencyApiResponse[] response = {};
        when(restTemplate.getForEntity(anyString(), any(Class.class)))
                .thenReturn(new ResponseEntity(response, HttpStatus.OK));

        // when
        assertThrows(CryptoCurrencyNotFoundException.class, () -> cryptoCurrencyApiClient.getCryptoCurrencyById(id));
    }
}