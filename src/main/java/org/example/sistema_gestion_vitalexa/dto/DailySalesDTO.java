package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailySalesDTO(
        LocalDate date,
        BigDecimal revenue,
        Integer orders
) {}
