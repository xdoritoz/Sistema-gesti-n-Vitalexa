package org.example.sistema_gestion_vitalexa.repository;

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
    // Ventas por vendedor
    List<Order> findByVendedorId(UUID vendedorId);

    // Ventas por rango de fechas
    List<Order> findByFechaBetween(LocalDateTime start, LocalDateTime end);

    // Ventas por estado
    List<Order> findByEstado(OrdenStatus estado);

    // Total vendido por vendedor
    @Query("""
        SELECT COALESCE(SUM(o.total), 0)
        FROM Order o
        WHERE o.vendedor.id = :vendedorId
    """)
    BigDecimal totalVendidoPorVendedor(Long vendedorId);

    // Total vendido en un rango
    @Query("""
        SELECT COALESCE(SUM(o.total), 0)
        FROM Order o
        WHERE o.fecha BETWEEN :start AND :end
    """)
    BigDecimal totalVendidoEntreFechas(LocalDateTime start, LocalDateTime end);

    @Query("""
    SELECT 
        EXTRACT(YEAR FROM o.fecha),
        EXTRACT(MONTH FROM o.fecha),
        COALESCE(SUM(o.total), 0)
    FROM Order o
    GROUP BY 
        EXTRACT(YEAR FROM o.fecha),
        EXTRACT(MONTH FROM o.fecha)
    ORDER BY 1, 2
""")
    List<Object[]> totalPorMes();

    @Query("""
    SELECT o.vendedor.username, COALESCE(SUM(o.total), 0)
    FROM Order o
    GROUP BY o.vendedor.username
    ORDER BY SUM(o.total) DESC
""")
    List<Object[]> topVendedores();

    List<Order> findAll();

    List<Order> findByVendedor(User vendedor);
    Optional<Order> findByIdAndVendedorUsername(UUID id, String username);

}
