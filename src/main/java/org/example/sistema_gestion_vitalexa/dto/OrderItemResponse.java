package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        String productName,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
