package com.idfinance.lab.cryptocurrency.repository;

import com.idfinance.lab.cryptocurrency.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link CryptoCurrencyRepository} interface provides CRUD operations for managing cryptocurrency entities.
 */
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {

    /**
     * Retrieves a cryptocurrency entity by its symbol.
     * @param symbol The symbol of the cryptocurrency.
     * @return  The {@link CryptoCurrency} entity with the specified symbol, or {@code null} if not found.
     */
    CryptoCurrency findBySymbol(String symbol);
}
