package org.example.sistema_gestion_vitalexa.dto;

public record LowStockProductDTO(
        String productId,
        String productName,
        Integer currentStock,
        String status
) {}
