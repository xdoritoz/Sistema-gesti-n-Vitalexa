package org.example.sistema_gestion_vitalexa.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.OrderRequestDto;
import org.example.sistema_gestion_vitalexa.dto.OrderResponse;
import org.example.sistema_gestion_vitalexa.entity.*;
import org.example.sistema_gestion_vitalexa.enums.OrdenStatus;
import org.example.sistema_gestion_vitalexa.exceptions.BusinessExeption;
import org.example.sistema_gestion_vitalexa.mapper.OrderMapper;
import org.example.sistema_gestion_vitalexa.repository.OrdenRepository;
import org.example.sistema_gestion_vitalexa.repository.UserRepository;
import org.example.sistema_gestion_vitalexa.service.ClientService;
import org.example.sistema_gestion_vitalexa.service.OrdenService;
import org.example.sistema_gestion_vitalexa.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductService productService;
    private final ClientService clientService;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    // =========================
    // CREATE ORDER (VENDEDOR)
    // =========================
    @Override
    public OrderResponse createOrder(OrderRequestDto request, String username) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new BusinessExeption("La venta debe tener al menos un producto");
        }

        User vendedor = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessExeption("Vendedor no encontrado"));

        Client client = null;
        if (request.clientId() != null) {
            client = clientService.findEntityById(request.clientId());
        }

        Order order = new Order(vendedor, client);

        // Agregar notas si existen
        if (request.notas() != null && !request.notas().isBlank()) {
            order.setNotas(request.notas());
        }

        request.items().forEach(itemReq -> {
            Product product = productService.findEntityById(itemReq.productId());

            // Permitir productos sin stock si hay una nota
            if (product.getStock() < itemReq.cantidad() &&
                    (request.notas() == null || request.notas().isBlank())) {
                throw new BusinessExeption("Stock insuficiente para: " + product.getNombre());
            }

            // Solo decrementar stock si hay suficiente
            if (product.getStock() >= itemReq.cantidad()) {
                product.decreaseStock(itemReq.cantidad());
            }

            OrderItem item = new OrderItem(product, itemReq.cantidad());
            order.addItem(item);
        });

        Order savedOrder = ordenRepository.save(order);

        if (client != null) {
            client.registerPurchase(savedOrder.getTotal());
        }

        return orderMapper.toResponse(savedOrder);
    }


    // =========================
    // ADMIN / OWNER
    // =========================
    @Override
    public List<OrderResponse> findAll() {
        return ordenRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse findById(UUID id) {
        Order order = ordenRepository.findById(id)
                .orElseThrow(() -> new BusinessExeption("Orden no encontrada"));
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse cambiarEstadoOrden(UUID id, OrdenStatus nuevoEstado) {

        Order order = ordenRepository.findById(id)
                .orElseThrow(() -> new BusinessExeption("Orden no encontrada"));

        order.setEstado(nuevoEstado);

        return orderMapper.toResponse(order);
    }

    // =========================
    // VENDEDOR (SEGURIDAD REAL)
    // =========================
    @Override
    public List<OrderResponse> findMyOrders(String username) {

        User vendedor = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessExeption("Usuario no encontrado"));

        return ordenRepository.findByVendedor(vendedor)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse findMyOrderById(UUID id, String username) {

        Order order = ordenRepository
                .findByIdAndVendedorUsername(id, username)
                .orElseThrow(() -> new BusinessExeption("Orden no encontrada"));

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(UUID orderId, OrderRequestDto request) {
        Order order = ordenRepository.findById(orderId)
                .orElseThrow(() -> new BusinessExeption("Orden no encontrada"));

        if (order.getEstado() == OrdenStatus.COMPLETADO ||
                order.getEstado() == OrdenStatus.CANCELADO) {
            throw new BusinessExeption("No se puede editar una orden completada o cancelada");
        }

        // Restaurar stock de items anteriores
        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.increaseStock(item.getCantidad());
        });

        // Limpiar items actuales
        order.getItems().clear();
        order.setTotal(BigDecimal.ZERO);

        // Agregar nuevos items
        request.items().forEach(itemReq -> {
            Product product = productService.findEntityById(itemReq.productId());

            if (product.getStock() >= itemReq.cantidad()) {
                product.decreaseStock(itemReq.cantidad());
            }

            OrderItem item = new OrderItem(product, itemReq.cantidad());
            order.addItem(item);
        });

        // Actualizar notas
        if (request.notas() != null) {
            order.setNotas(request.notas());
        }

        // Actualizar cliente si cambió
        if (request.clientId() != null) {
            Client newClient = clientService.findEntityById(request.clientId());
            order.setCliente(newClient);
        }

        Order updatedOrder = ordenRepository.save(order);
        return orderMapper.toResponse(updatedOrder);
    }

}
