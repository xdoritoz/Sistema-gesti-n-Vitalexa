package org.example.sistema_gestion_vitalexa.dto;

public record NotificationData(
        String orderId,
        String productId,
        String productName,
        Integer currentStock,
        Integer reorderPoint
) {}
