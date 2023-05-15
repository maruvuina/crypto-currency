package com.idfinance.lab.cryptocurrency.service;

import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * The {@link CryptoCurrencyService} interface provides operations for managing crypto currency.
 */
public interface CryptoCurrencyService {

    /**
     * Retrieves a list of all cryptocurrencies.
     * @return The list of {@link CryptoCurrencyDto}.
     */
    List<CryptoCurrencyDto> getAll();

    /**
     * Retrieves the price of a cryptocurrency based on its symbol.
     * @param symbol The symbol of the cryptocurrency.
     * @return The price of the cryptocurrency.
     */
    BigDecimal getPrice(String symbol);

    /**
     * Retrieves a cryptocurrency by its symbol.
     * @param symbol The symbol of the cryptocurrency.
     * @return The {@link CryptoCurrencyDto} with the specified symbol.
     */
    CryptoCurrencyDto getBySymbol(String symbol);

    /**
     * Notifies users about price changes in cryptocurrencies.
     */
    void notifyAboutPriceChange();
}
