package org.example.sistema_gestion_vitalexa.repository;

import org.example.sistema_gestion_vitalexa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByActiveTrue();
    List<Product> findByStockLessThanAndActiveTrue(int threshold);

}
