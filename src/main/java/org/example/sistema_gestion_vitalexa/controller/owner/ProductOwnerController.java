package org.example.sistema_gestion_vitalexa.controller.owner;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.CreateProductRequest;
import org.example.sistema_gestion_vitalexa.dto.ProductResponse;
import org.example.sistema_gestion_vitalexa.dto.UpdateProductRequest;
import org.example.sistema_gestion_vitalexa.exceptions.BusinessExeption;
import org.example.sistema_gestion_vitalexa.service.ProductImageService;
import org.example.sistema_gestion_vitalexa.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/owner/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class ProductOwnerController {

    private static final Logger log = LoggerFactory.getLogger(ProductOwnerController.class);

    private final ProductService productService;
    private final ProductImageService imageService;

    /**
     * Obtener todos los productos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAllAdmin());
    }

    /**
     * Crear nuevo producto con imagen
     */
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam BigDecimal precio,
            @RequestParam Integer stock,
            @RequestParam(required = false) MultipartFile image) {
        try {
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.saveImage(image);
            }

            CreateProductRequest request = new CreateProductRequest(nombre, descripcion, precio, stock, imageUrl);
            ProductResponse response = productService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualizar producto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) BigDecimal precio,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) Boolean active) {
        try {
            log.debug("Actualizando producto ID: {} con nombre: {}", id, nombre);

            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.saveImage(image);
            }

            UpdateProductRequest request = new UpdateProductRequest(nombre, descripcion, precio, stock, imageUrl, active);
            ProductResponse response = productService.update(id, request);
            log.info("Producto actualizado exitosamente: {}", id);
            return ResponseEntity.ok(response);
        } catch (BusinessExeption e) {
            log.warn("Error de negocio al actualizar producto: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("Error al procesar imagen", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error inesperado al actualizar producto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cambiar estado de un producto (activo/inactivo)
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> changeStatus(
            @PathVariable UUID id,
            @RequestParam boolean activo) {
        productService.changeStatus(id, activo);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar (soft delete) o hard delete si se pasa ?hard=true
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, @RequestParam(name = "hard", required = false, defaultValue = "false") boolean hard) {
        try {
            if (hard) {
                productService.hardDelete(id);
            } else {
                productService.softDelete(id);
            }
            return ResponseEntity.noContent().build();
        } catch (BusinessExeption be) {
            log.warn("Business error deleting product {}: {}", id, be.getMessage());
            return ResponseEntity.badRequest().body(be.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error deleting product {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error eliminando producto");
        }
    }

    /**
     * Obtener producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        ProductResponse producto = productService.findById(id);
        return ResponseEntity.ok(producto);
    }
}
