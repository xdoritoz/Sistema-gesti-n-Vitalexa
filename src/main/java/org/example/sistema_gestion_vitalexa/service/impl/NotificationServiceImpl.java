package org.example.sistema_gestion_vitalexa.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sistema_gestion_vitalexa.dto.*;
import org.example.sistema_gestion_vitalexa.service.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNewOrderNotification(String orderId, String vendorName, String clientName) {
        String title = "Nueva Orden Recibida";
        String message = String.format("Nueva orden #%s creada por %s para cliente %s",
                orderId.substring(0, 8), vendorName, clientName);
        String targetUrl = "/admin/orders/" + orderId;

        NotificationData data = new NotificationData(orderId, null, null, null, null);
        NotificationDTO notification = createNotification(
                NotificationType.NEW_ORDER,
                title,
                message,
                targetUrl,
                data
        );

        // Enviar a admins y owners
        messagingTemplate.convertAndSend("/topic/admin/notifications", notification);
        messagingTemplate.convertAndSend("/topic/owner/notifications", notification);

        log.info("Notificación enviada: Nueva orden {}", orderId);
    }

    @Override
    public void sendOrderCompletedNotification(String orderId) {
        String title = "Orden Completada";
        String message = String.format("La orden #%s ha sido completada exitosamente", orderId.substring(0, 8));
        String targetUrl = "/orders/" + orderId;

        NotificationData data = new NotificationData(orderId, null, null, null, null);
        NotificationDTO notification = createNotification(
                NotificationType.ORDER_COMPLETED,
                title,
                message,
                targetUrl,
                data
        );

        messagingTemplate.convertAndSend("/topic/notifications", notification);
        log.info("Notificación enviada: Orden completada {}", orderId);
    }

    @Override
    public void sendLowStockAlert(String productId, String productName, int currentStock, int reorderPoint) {
        String title = "⚠️ Stock Bajo";
        String message = String.format("El producto '%s' tiene solo %d unidades. Punto de reorden: %d",
                productName, currentStock, reorderPoint);
        String targetUrl = "/products/" + productId;

        NotificationData data = new NotificationData(null, productId, productName, currentStock, reorderPoint);
        NotificationDTO notification = createNotification(
                NotificationType.LOW_STOCK,
                title,
                message,
                targetUrl,
                data
        );

        messagingTemplate.convertAndSend("/topic/admin/notifications", notification);
        messagingTemplate.convertAndSend("/topic/owner/notifications", notification);

        log.warn("Alerta de stock bajo: {} - Stock: {}/{}", productName, currentStock, reorderPoint);
    }

    @Override
    public void sendOutOfStockAlert(String productId, String productName) {
        String title = "🚨 Sin Stock";
        String message = String.format("El producto '%s' se ha quedado sin stock", productName);
        String targetUrl = "/products/" + productId;

        NotificationData data = new NotificationData(null, productId, productName, 0, null);
        NotificationDTO notification = createNotification(
                NotificationType.OUT_OF_STOCK,
                title,
                message,
                targetUrl,
                data
        );

        messagingTemplate.convertAndSend("/topic/admin/notifications", notification);
        messagingTemplate.convertAndSend("/topic/owner/notifications", notification);

        log.error("Alerta: Producto sin stock - {}", productName);
    }

    @Override
    public NotificationDTO createNotification(NotificationType type, String title, String message, String targetUrl, Object data) {
        return new NotificationDTO(
                UUID.randomUUID().toString(),
                type,
                title,
                message,
                targetUrl,
                LocalDateTime.now(),
                false,
                (NotificationData) data
        );
    }
}
