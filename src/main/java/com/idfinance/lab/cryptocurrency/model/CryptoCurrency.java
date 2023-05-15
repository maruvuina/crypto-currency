package com.idfinance.lab.cryptocurrency.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_currencies")
public class CryptoCurrency {

    @Id
    private Long id;

    @Column(nullable = false, length = 5)
    private String symbol;

    @Column(name = "actual_price", nullable = false)
    private BigDecimal actualPrice;

    @OneToMany(mappedBy = "cryptoCurrency", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();
}
