package com.idfinance.lab.cryptocurrency.controller;


import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;
import com.idfinance.lab.cryptocurrency.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CryptoCurrencyController {

    private final CryptoCurrencyService cryptoCurrencyService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CryptoCurrencyDto> getAll() {
        return cryptoCurrencyService.getAll();
    }

    @GetMapping("/{symbol}")
    @ResponseStatus(code = HttpStatus.OK)
    public BigDecimal getPrice(@PathVariable String symbol) {
        return cryptoCurrencyService.getPrice(symbol);
    }
}
