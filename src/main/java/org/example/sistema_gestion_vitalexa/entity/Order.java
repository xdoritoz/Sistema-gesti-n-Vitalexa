package org.example.sistema_gestion_vitalexa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Fecha de la venta
    @Column(nullable = false)
    private LocalDateTime fecha;

    // Total de la venta
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    // Estado del pedido
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrdenStatus estado;

    // Vendedor que realizó la venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private User vendedor;

    // Cliente al que se le vendió
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client cliente;

    // Detalle de productos vendidos
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
        this.estado = OrdenStatus.CONFIRMADO;
        this.total = BigDecimal.ZERO;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        recalculatetotal();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        recalculatetotal();
    }

    public void recalculatetotal() {
        this.total = items.stream()
            .map(OrderItem::getSubTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }




}
