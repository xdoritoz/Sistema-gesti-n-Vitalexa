package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record TopProductDTO(
        String productId,
        String productName,
        Integer quantitySold,
        BigDecimal revenue,
        String imageUrl
) {}
