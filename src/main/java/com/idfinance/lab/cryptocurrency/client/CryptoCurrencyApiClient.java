package com.idfinance.lab.cryptocurrency.client;

import com.idfinance.lab.cryptocurrency.dto.CryptoCurrencyApiResponse;
import com.idfinance.lab.cryptocurrency.exception.CryptoCurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptoCurrencyApiClient {

    private static final String QUERY_PARAM_ID = "id";

    private final RestTemplate webClient;

    public CryptoCurrencyApiResponse getCryptoCurrencyById(Long id) {
        CryptoCurrencyApiResponse[] response = webClient.getForEntity("?" + QUERY_PARAM_ID + "=" + id,
                CryptoCurrencyApiResponse[].class).getBody();
        Objects.requireNonNull(response, "Response body is null");
        if (response.length == 0) {
            log.error("There is no cryptocurrency for id = {}", id);
            throw new CryptoCurrencyNotFoundException("There is no cryptocurrency for id = " + id);
        }
        return response[0];
    }
}
