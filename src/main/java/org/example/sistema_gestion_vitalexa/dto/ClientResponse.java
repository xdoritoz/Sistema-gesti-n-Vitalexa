package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        String nombre,
        BigDecimal totalCompras,
        LocalDateTime ultimaCompra
) {
}
