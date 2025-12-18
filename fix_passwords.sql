CHAC-- Script SQL para actualizar contraseñas de usuarios a BCrypt hash
-- Ejecutar este script en PostgreSQL para la base de datos inventory_db
-- Las contraseñas "1234" serán reemplazadas por su equivalente BCrypt

UPDATE users
SET password = '$2a$10$T2sB7VNT5FMRmWnFXUEWje0eGVV6tL9w/LHyL2g7T8.Q3Y7Ky0bpC'
WHERE username IN ('owner', 'admin', 'vendedor');

-- Verificar que la actualización fue correcta
SELECT id, username, password, role, active FROM users;
O