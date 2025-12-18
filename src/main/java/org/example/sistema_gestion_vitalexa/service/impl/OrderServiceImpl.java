package org.example.sistema_gestion_vitalexa.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.OrderItemRequestDTO;
import org.example.sistema_gestion_vitalexa.dto.OrderRequestDto;
import org.example.sistema_gestion_vitalexa.dto.OrderResponse;
import org.example.sistema_gestion_vitalexa.entity.*;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;
import org.example.sistema_gestion_vitalexa.exceptions.BusinessExeption;
import org.example.sistema_gestion_vitalexa.repository.ClientRepository;
import org.example.sistema_gestion_vitalexa.repository.OrdenItemRepository;
import org.example.sistema_gestion_vitalexa.repository.OrdenRepository;
import org.example.sistema_gestion_vitalexa.repository.ProductRepository;
import org.example.sistema_gestion_vitalexa.service.OrdenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrdenService {
    private final OrdenRepository orderRepository;
    private final OrdenItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final UserServiceImpl userService;
    private final ClientServiceImpl clientService;
    private final ProductServiceImpl productService;

    @Override
    @Transactional
    public Order confirmarVenta(Order order) {

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new BusinessExeption("La venta debe tener al menos un producto");
        }

        // Validar stock y calcular totales
        for (OrderItem item : order.getItems()) {

            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new BusinessExeption("Producto no encontrado"));

            if (product.getStock() < item.getCantidad()) {
                throw new BusinessExeption(
                        "Stock insuficiente para el producto: " + product.getNombre()
                );
            }

            // Descontar stock
            product.setStock(product.getStock() - item.getCantidad());
            productRepository.save(product);

            // Precio fijo al momento de la venta
            item.setPrecioUnitario(product.getPrecio());
            item.calcularSubTotal();
            item.setOrder(order);
        }

        // Recalcular total
        order.recalculatetotal();

        // Actualizar cliente
        Client client = order.getCliente();
        client.setTotalCompras(
                client.getTotalCompras().add(order.getTotal())
        );
        client.setUltimaCompra(order.getFecha());
        clientRepository.save(client);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order cambiarEstadoOrden(UUID orderId, OrdenStatus nuevoEstado) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessExeption("Venta no encontrada"));

        order.setEstado(nuevoEstado);

        return orderRepository.save(order);
    }


}
