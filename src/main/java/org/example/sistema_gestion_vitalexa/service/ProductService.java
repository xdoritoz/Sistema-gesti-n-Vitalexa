package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.dto.CreateProductRequest;
import org.example.sistema_gestion_vitalexa.dto.ProductResponse;
import org.example.sistema_gestion_vitalexa.dto.UpdateProductRequest;
import org.example.sistema_gestion_vitalexa.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(UUID id, UpdateProductRequest request);
    void softDelete(UUID id);
    void hardDelete(UUID id);
    List<ProductResponse> findAllAdmin();
    List<ProductResponse> findAllActive();
    Product findEntityById(UUID id);
    ProductResponse findById(UUID id);
    void changeStatus(UUID id, boolean status);

}
