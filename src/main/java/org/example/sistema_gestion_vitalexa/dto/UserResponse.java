package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String role
) {
}
