package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID productId,
        String productName,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
