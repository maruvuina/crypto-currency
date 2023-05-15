package com.idfinance.lab.cryptocurrency.mapper;

import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyApiResponse;
import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;
import com.idfinance.lab.cryptocurrency.model.CryptoCurrency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The {@link CryptoCurrencyMapper} interface provides mapping operations between
 * {@link CryptoCurrency} and {@link CryptoCurrencyDto}, as well as between
 * {@link CryptoCurrencyDto} and {@link CryptoCurrencyApiResponse}.
 */
@Mapper(componentModel = "spring")
public interface CryptoCurrencyMapper {

    /**
     * Maps a {@link CryptoCurrency} object to a {@link CryptoCurrencyDto} object.
     * @param cryptoCurrency The {@link CryptoCurrency} entity to map.
     * @return The mapped {@link CryptoCurrencyDto}.
     */
    CryptoCurrencyDto mapToDto(CryptoCurrency cryptoCurrency);

    /**
     * Maps a {@link CryptoCurrencyDto} object to a {@link CryptoCurrency} object.
     * @param cryptoCurrencyDto The {@link CryptoCurrencyDto} to map.
     * @return The mapped {@link CryptoCurrency} entity.
     */
    CryptoCurrency mapFromDto(CryptoCurrencyDto cryptoCurrencyDto);

    /**
     * Maps a {@link CryptoCurrencyApiResponse} object to a {@link CryptoCurrency} object.
     * @param cryptoCurrencyApiResponse The {@link CryptoCurrencyApiResponse} response to map.
     * @return The mapped {@link CryptoCurrency} entity.
     */
    @Mapping(target = "actualPrice", source = "priceUsd")
    CryptoCurrency mapFromCryptoCurrencyApiResponse(CryptoCurrencyApiResponse cryptoCurrencyApiResponse);
}
