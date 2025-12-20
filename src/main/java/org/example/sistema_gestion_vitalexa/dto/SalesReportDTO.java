package org.example.sistema_gestion_vitalexa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record SalesReportDTO(
        BigDecimal totalRevenue,
        BigDecimal averageOrderValue,
        Integer totalOrders,
        Integer completedOrders,
        Integer pendingOrders,
        Integer canceledOrders,
        List<DailySalesDTO> dailySales,
        List<MonthlySalesDTO> monthlySales
) {}

