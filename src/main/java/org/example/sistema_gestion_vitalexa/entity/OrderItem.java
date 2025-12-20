package org.example.sistema_gestion_vitalexa.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private BigDecimal subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @PrePersist
    @PreUpdate
    public void calcularSubTotal() {
        if (precioUnitario != null && cantidad != null) {
            this.subTotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }


    public OrderItem(Product product, Integer cantidad) {
        this.product = product;
        this.cantidad = cantidad;
        this.precioUnitario = product.getPrecio();
        this.subTotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
