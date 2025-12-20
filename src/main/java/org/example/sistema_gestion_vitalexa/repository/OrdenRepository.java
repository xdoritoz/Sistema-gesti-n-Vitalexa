package org.example.sistema_gestion_vitalexa.repository;

import org.example.sistema_gestion_vitalexa.entity.Client;
import org.example.sistema_gestion_vitalexa.entity.Order;
import org.example.sistema_gestion_vitalexa.entity.User;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdenRepository extends JpaRepository <Order, UUID> {
    List<Order> findByVendedor(User vendedor);
    Optional<Order> findByIdAndVendedorUsername(UUID id, String username);
    List<Order> findByEstado(OrdenStatus estado);
    List<Order> findByFechaBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByCliente(Client client);
}
