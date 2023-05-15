package com.idfinance.lab.cryptocurrency.service.impl;

import com.idfinance.lab.cryptocurrency.client.CryptoCurrencyApiClient;
import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyDto;
import com.idfinance.lab.cryptocurrency.exception.CryptoCurrencyNotFoundException;
import com.idfinance.lab.cryptocurrency.mapper.CryptoCurrencyMapper;
import com.idfinance.lab.cryptocurrency.model.CryptoCurrency;
import com.idfinance.lab.cryptocurrency.repository.CryptoCurrencyRepository;
import com.idfinance.lab.cryptocurrency.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private static final int PRICE_PERCENTAGE_CHANGE_THRESHOLD = 1;

    private static final int PERCENTAGE_CHANGE_FACTOR = 100;

    private static final int ROUNDING_PRECISION = 4;

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    private final CryptoCurrencyApiClient cryptoCurrencyApiClient;

    @Override
    public List<CryptoCurrencyDto> getAll() {
        return cryptoCurrencyRepository.findAll().stream()
                .map(cryptoCurrencyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getPrice(String symbol) {
        return getBySymbol(symbol).getActualPrice();
    }

    @Override
    public CryptoCurrencyDto getBySymbol(String symbol) {
        return cryptoCurrencyMapper.mapToDto(cryptoCurrencyRepository.findBySymbol(symbol));
    }

    @Override
    @Scheduled(fixedDelayString = "${cryptocurrency.schedule.fixedDelay}")
    public void notifyAboutPriceChange() {
        List<CryptoCurrency> oldCryptoCurrencyPrices = cryptoCurrencyRepository.findAll();
        List<CryptoCurrency> actualCryptoCurrencyPrices = getActualCryptoCurrencyPrices(oldCryptoCurrencyPrices);

        oldCryptoCurrencyPrices.forEach(cryptoCurrency ->
                cryptoCurrency.getUsers().forEach(user -> {
                    BigDecimal actualPrice = getActualPrice(actualCryptoCurrencyPrices, cryptoCurrency.getSymbol());
                    float priceChange = calculatePercentageChange(actualPrice, user.getUserPrice());
                    if (Math.abs(priceChange) >= PRICE_PERCENTAGE_CHANGE_THRESHOLD) {
                        log.warn("For the user {} price for {} changed by more than 1%, {}% change from initial price",
                                user.getUsername(), user.getCryptoCurrency().getSymbol(), priceChange);
                    }
                }));

        cryptoCurrencyRepository.saveAll(actualCryptoCurrencyPrices);
    }

    private List<CryptoCurrency> getActualCryptoCurrencyPrices(List<CryptoCurrency> oldCryptoCurrencyPrices) {
        return oldCryptoCurrencyPrices.stream()
                .map(cryptoCurrency -> cryptoCurrencyApiClient.getCryptoCurrencyById(cryptoCurrency.getId()))
                .map(cryptoCurrencyMapper::mapFromCryptoCurrencyApiResponse)
                .collect(Collectors.toList());
    }

    private BigDecimal getActualPrice(List<CryptoCurrency> actualCryptoCurrencyPrices, String cryptoCurrencySymbol) {
        return actualCryptoCurrencyPrices.stream()
                .filter(cryptoCurrencyApiResponse -> cryptoCurrencyApiResponse.getSymbol().equals(cryptoCurrencySymbol))
                .findFirst()
                .map(CryptoCurrency::getActualPrice)
                .orElseThrow(() -> new CryptoCurrencyNotFoundException("There is no crypto currency: " + cryptoCurrencySymbol));
    }

    private float calculatePercentageChange(BigDecimal newValue, BigDecimal oldValue) {
        return ((newValue.subtract(oldValue))
                .divide(oldValue, ROUNDING_PRECISION, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(PERCENTAGE_CHANGE_FACTOR))
                .floatValue();
    }
}
