package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.entity.Order;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;

import java.util.UUID;

public interface OrdenService {
    Order confirmarVenta(Order orden);
    Order cambiarEstadoOrden(UUID ordenId, OrdenStatus nuevoEstado);
}
