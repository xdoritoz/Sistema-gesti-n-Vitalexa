package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        String imageUrl,
        Boolean active
) {
}
