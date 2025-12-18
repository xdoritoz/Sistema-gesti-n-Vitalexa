package org.example.sistema_gestion_vitalexa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.OrderResponse;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;
import org.example.sistema_gestion_vitalexa.service.OrdenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','OWNER')")
public class OrderAdminController {

    private final OrdenService ordenService;

    @GetMapping
    public List<OrderResponse> findAll() {
        return ordenService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id) {
        return ordenService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public OrderResponse changeStatus(
            @PathVariable UUID id,
            @RequestParam OrdenStatus status
    ) {
        return ordenService.cambiarEstadoOrden(id, status);
    }
}
