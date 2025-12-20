package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record TopClientDTO(
        String clientId,
        String clientName,
        String clientPhone,
        BigDecimal totalSpent,
        Integer totalOrders
) {}
