package org.example.sistema_gestion_vitalexa.mapper;

import org.example.sistema_gestion_vitalexa.dto.OrderItemResponse;
import org.example.sistema_gestion_vitalexa.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nombre", target = "productName")
    @Mapping(target = "subtotal", expression = "java(item.getSubTotal())")
    OrderItemResponse toResponse(OrderItem item);
}
