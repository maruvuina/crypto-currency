package com.idfinance.lab.cryptocurrency.exception.handler;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class ErrorResponse {

    private Instant timestamp;

    private String error;

    private String message;

    private String trace;

}
