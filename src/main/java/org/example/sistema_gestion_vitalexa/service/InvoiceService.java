package org.example.sistema_gestion_vitalexa.service;

import java.util.UUID;

public interface InvoiceService {

    /**
     * Genera un PDF de factura/orden para impresión o visualización del empacador
     */
    byte[] generateOrderInvoicePdf(UUID orderId);

    /**
     * Genera factura y la envía por email al cliente (próxima funcionalidad)
     */
    void sendInvoiceByEmail(UUID orderId);
}
