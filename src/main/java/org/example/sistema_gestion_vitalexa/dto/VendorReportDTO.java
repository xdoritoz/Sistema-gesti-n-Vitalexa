package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.util.List;

public record VendorReportDTO(
        Integer totalVendors,
        List<VendorPerformanceDTO> topVendors
) {}

