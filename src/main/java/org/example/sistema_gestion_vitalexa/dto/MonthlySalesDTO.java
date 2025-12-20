package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record MonthlySalesDTO(
        String month,
        Integer year,
        BigDecimal revenue,
        Integer orders
) {}
