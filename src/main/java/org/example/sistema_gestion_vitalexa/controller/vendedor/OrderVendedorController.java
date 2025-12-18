package org.example.sistema_gestion_vitalexa.controller.vendedor;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.OrderResponse;
import org.example.sistema_gestion_vitalexa.service.OrdenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.example.sistema_gestion_vitalexa.dto.OrderRequestDto;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vendedor/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('VENDEDOR')")
public class OrderVendedorController {

    private final OrdenService ordenService;

    // 🔹 Crear venta
    @PostMapping
    public OrderResponse create(
            @Valid @RequestBody OrderRequestDto request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return ordenService.createOrder(request, username);
    }

    // 🔹 Ver MIS órdenes
    @GetMapping("/my")
    public List<OrderResponse> findMyOrders(Authentication authentication) {
        String username = authentication.getName();
        return ordenService.findMyOrders(username);
    }

    // 🔹 Ver detalle de MI orden
    @GetMapping("/{id}")
    public OrderResponse findMyOrderById(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        return ordenService.findMyOrderById(id, authentication.getName());
    }
}

