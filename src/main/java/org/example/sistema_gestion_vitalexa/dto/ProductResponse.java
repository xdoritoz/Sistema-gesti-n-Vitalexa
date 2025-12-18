package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        String imageUrl,
        Boolean active
) {
}
