package org.example.sistema_gestion_vitalexa.repository;

import org.example.sistema_gestion_vitalexa.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrdenItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByVendedorId(UUID vendedorId);

}
