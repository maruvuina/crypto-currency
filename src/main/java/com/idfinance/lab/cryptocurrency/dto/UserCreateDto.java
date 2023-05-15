package com.idfinance.lab.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Symbol must not be blank")
    private String symbol;
}
