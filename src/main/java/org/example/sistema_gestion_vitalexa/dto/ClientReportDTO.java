package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.util.List;

public record ClientReportDTO(
        Integer totalClients,
        Integer activeClients,
        List<TopClientDTO> topClients
) {}

