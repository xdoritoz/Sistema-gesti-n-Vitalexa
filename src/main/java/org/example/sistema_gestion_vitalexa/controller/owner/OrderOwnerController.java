package org.example.sistema_gestion_vitalexa.controller.owner;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.OrderResponse;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;
import org.example.sistema_gestion_vitalexa.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/owner/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class OrderOwnerController {

    private final OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = ordenService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id) {
        OrderResponse order = ordenService.findById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<OrderResponse>> getCompletedOrders() {
        List<OrderResponse> orders = ordenService.findByEstado(OrdenStatus.COMPLETADO);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<OrderResponse>> getPendingOrders() {
        List<OrderResponse> orders = ordenService.findByEstado(OrdenStatus.PENDIENTE);
        return ResponseEntity.ok(orders);
    }
}
