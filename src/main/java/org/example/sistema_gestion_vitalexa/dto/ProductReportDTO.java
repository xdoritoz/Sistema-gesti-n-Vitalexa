package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductReportDTO(
        Integer totalProducts,
        Integer activeProducts,
        Integer inactiveProducts,
        Integer lowStockProducts,
        BigDecimal totalInventoryValue,
        List<TopProductDTO> topSellingProducts,
        List<LowStockProductDTO> lowStockDetails

) {}

