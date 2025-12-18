package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record VentasPorVendedorDTO(
        String vendedor,
        BigDecimal total
) {
}
