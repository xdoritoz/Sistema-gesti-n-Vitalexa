package org.example.sistema_gestion_vitalexa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads/products}")
    private String uploadDir;

    /**
     * Sirve archivos estáticos de uploads/ en desarrollo local
     * En producción (Cloudinary) esto no se usa
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Solo para desarrollo local
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/")
                .addResourceLocations("file:uploads/");
    }
}
