package org.example.sistema_gestion_vitalexa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.entity.Product;
import org.example.sistema_gestion_vitalexa.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','OWNER')")
public class ProductAdminController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable UUID id, @RequestBody Product product) {
        Product existing = productService.findById(id);
        existing.setNombre(product.getNombre());
        existing.setDescripcion(product.getDescripcion());
        existing.setPrecio(product.getPrecio());
        existing.setStock(product.getStock());
        existing.setImageUrl(product.getImageUrl());
        return ResponseEntity.ok(productService.save(existing)) ;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Product product = productService.findById(id);
        product.setActive(false); // soft delete
        productService.save(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
