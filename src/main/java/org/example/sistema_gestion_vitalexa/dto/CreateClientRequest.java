package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateClientRequest(
        UUID id,
        String nombre,
        BigDecimal totalCompras,
        LocalDateTime ultimaCompra
) {
}
