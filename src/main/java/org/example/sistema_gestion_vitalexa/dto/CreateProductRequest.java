package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;


public record CreateProductRequest(
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        Integer reorderPoint,  // ← Posición 5
        String imageUrl        // ← Posición 6
) {
}
