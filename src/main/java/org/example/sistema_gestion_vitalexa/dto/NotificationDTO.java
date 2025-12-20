package org.example.sistema_gestion_vitalexa.dto;

import java.time.LocalDateTime;

public record NotificationDTO(
        String id,
        NotificationType type,
        String title,
        String message,
        String targetUrl,
        LocalDateTime timestamp,
        boolean read,
        NotificationData data
) {}

