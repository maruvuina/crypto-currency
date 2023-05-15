package com.idfinance.lab.cryptocurrency.exception;

public class CryptoCurrencyNotFoundException extends RuntimeException {

    public CryptoCurrencyNotFoundException(String message) {
        super(message);
    }
}
