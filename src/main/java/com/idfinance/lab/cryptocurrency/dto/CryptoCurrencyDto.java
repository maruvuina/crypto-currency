package com.idfinance.lab.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyDto {

    private Long id;

    private String symbol;

    private BigDecimal actualPrice;
}
