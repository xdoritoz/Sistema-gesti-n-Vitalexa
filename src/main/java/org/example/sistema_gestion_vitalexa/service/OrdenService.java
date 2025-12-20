package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.dto.OrderRequestDto;
import org.example.sistema_gestion_vitalexa.dto.OrderResponse;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;

import java.util.List;
import java.util.UUID;

public interface OrdenService {

    // 🔹 Crear orden (VENDEDOR)
    OrderResponse createOrder(OrderRequestDto request, String username);

    // 🔹 Cambiar estado (ADMIN / OWNER)
    OrderResponse cambiarEstadoOrden(UUID orderId, OrdenStatus nuevoEstado);

    // 🔹 ADMIN / OWNER
    OrderResponse findById(UUID orderId);
    List<OrderResponse> findAll();

    // 🔹 VENDEDOR (solo sus órdenes)
    List<OrderResponse> findMyOrders(String username);
    OrderResponse findMyOrderById(UUID id, String username);

    OrderResponse updateOrder(UUID orderId, OrderRequestDto request);
    List<OrderResponse> findByEstado(OrdenStatus estado);

}
