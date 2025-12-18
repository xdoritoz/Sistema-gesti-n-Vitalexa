package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product findById(UUID id);
    Product save(Product product);
    List<Product> findAll();
}
