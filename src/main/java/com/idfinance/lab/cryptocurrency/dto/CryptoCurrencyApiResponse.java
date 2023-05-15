package com.idfinance.lab.cryptocurrency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyApiResponse {

    private String id;

    private String symbol;

    @JsonProperty("price_usd")
    private BigDecimal priceUsd;
}
