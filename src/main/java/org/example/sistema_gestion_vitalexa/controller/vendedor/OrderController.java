package org.example.sistema_gestion_vitalexa.controller.vendedor;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.OrderRequestDto;
import org.example.sistema_gestion_vitalexa.entity.*;
import org.example.sistema_gestion_vitalexa.service.ClientService;
import org.example.sistema_gestion_vitalexa.service.OrdenService;
import org.example.sistema_gestion_vitalexa.service.ProductService;
import org.example.sistema_gestion_vitalexa.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrdenService orderService;
    private final ProductService productService;
    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('VENDEDOR')")
    public Order crearVenta(
            @Valid @RequestBody OrderRequestDto dto
    ) {

        User vendedor = userService.getAuthenticatedUser();
        Client client = clientService.findById(dto.clientId());

        Order order = new Order();
        order.setVendedor(vendedor);
        order.setCliente(client);

        dto.items().forEach(itemDTO -> {
            Product product = productService.findById(itemDTO.productId());

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .cantidad(itemDTO.cantidad())
                    .build();

            order.addItem(item);
        });

        return orderService.confirmarVenta(order);
    }


}
