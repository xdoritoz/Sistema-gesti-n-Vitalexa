package org.example.sistema_gestion_vitalexa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    private BigDecimal totalCompras = BigDecimal.ZERO;

    private String email;

    private LocalDateTime ultimaCompra;

    private String direccion;

    private String telefono;

    private boolean active = true;


    // ✅ ARREGLAR ESTE MÉTODO
    public void registerPurchase(BigDecimal monto) {
        // Verificar que totalCompras no sea null antes de sumar
        if (this.totalCompras == null) {
            this.totalCompras = BigDecimal.ZERO;
        }
        this.totalCompras = this.totalCompras.add(monto);
        this.ultimaCompra = LocalDateTime.now();
    }
}
