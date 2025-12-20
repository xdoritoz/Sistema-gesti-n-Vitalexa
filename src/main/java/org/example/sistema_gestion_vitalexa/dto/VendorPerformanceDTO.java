package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;

public record VendorPerformanceDTO(
        String vendorId,
        String vendorName,
        Integer totalOrders,
        BigDecimal totalRevenue,
        BigDecimal averageOrderValue
) {}
