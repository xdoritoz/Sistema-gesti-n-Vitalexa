# Sistema de Gestión Vitalexa – Backend

Backend empresarial desarrollado en **Java + Spring Boot** para la gestión integral de **ventas, inventario, clientes y métricas**, con control de acceso por roles, autenticación JWT y exportación de reportes.

---

## 📌 Características principales

- Arquitectura modular basada en roles
- Gestión de órdenes **end-to-end**
- CRM básico para clientes
- Inventario en tiempo real
- Facturación automática en PDF
- Reportes ejecutivos y exportación (PDF / Excel / CSV)
- Comunicación en tiempo real con **WebSockets**
- Seguridad basada en **JWT + Spring Security**

---

## 🧠 Arquitectura y stack técnico

### Backend
- **Java 17+**
- **Spring Boot**
- Spring Security + JWT
- Spring Data JPA
- MapStruct (Entity ⇆ DTO)
- Lombok
- PostgreSQL
- Flyway / Liquibase (migraciones)

### Infraestructura
- Docker
- Railway (deploy backend)
- Configuración por entornos (`dev`, `prod`)

---

## 🗂️ Estructura del proyecto

src/main/java/org/example/sistema_gestion_vitalexa
├── config
├── controller
│ ├── admin
│ ├── owner
│ ├── vendedor
│ ├── AuthController
│ ├── EmpacadorController
│ ├── ImageController
│ ├── ReportController
│ └── ReportExportController
├── dto
├── entity
├── enums
├── exceptions
├── mapper
├── repository
├── security
└── service
src/main/resources
├── application.properties
├── application-dev.properties
├── application-prod.properties
├── db/migration
└── static/images

---

## 🔐 Seguridad y autenticación

- Autenticación basada en **JWT**
- Control de acceso por roles:
  - `ADMIN`
  - `OWNER`
  - `VENDEDOR`
  - `EMPACADOR`
- Filtros y configuración en el módulo `security`

---

## 📅 Convenciones generales

- Prefijo global: `/api`
- Fechas en reportes: `yyyy-MM-dd` (ISO DATE)
- Descargas y previews:
  - `Content-Disposition: attachment` → descarga
  - `Content-Disposition: inline` → vista en navegador

---

## 🔑 Autenticación

### AuthController – `/api/auth`

| Método | Endpoint | Descripción |
|------|--------|------------|
| POST | `/login` | Autenticación y generación de JWT |

---

## 👑 ADMIN / OWNER

### Clientes – `/api/admin/clients`
- Obtener listado completo de clientes

### Órdenes – `/api/admin/orders`
- Listar, consultar, editar y cambiar estado
- Descargar o previsualizar factura en PDF

### Productos – `/api/admin/products`
- CRUD completo
- Manejo de imágenes (`multipart/form-data`)
- Soft delete / Hard delete
- Activación y desactivación de productos

### Metas de ventas – `/api/admin/sale-goals`
- Gestión completa de metas
- Consulta por mes y año

---

## 👔 OWNER

### Órdenes – `/api/owner/orders`
- Consulta general, pendientes y completadas

### Productos – `/api/owner/products`
- Control de inventario
- Alertas de bajo stock

### Metas de ventas – `/api/owner/sale-goals`

### Reportes – `/api/owner/reports`
- Ventas
- Productos
- Clientes
- Vendedores
- Reporte completo del negocio

---

## 🧑‍💼 VENDEDOR

### Clientes – `/api/vendedor/clients`
- Crear y gestionar clientes

### Órdenes – `/api/vendedor/orders`
- Crear órdenes
- Consultar historial propio

### Productos – `/api/vendedor/products`
- Consulta de productos activos

### Metas – `/api/vendedor/sale-goals`
- Meta actual
- Historial de metas

---

## 📦 EMPACADOR

### Empacador – `/api/empacador`
- Visualización de productos disponibles
- Gestión de reembolsos
- Registro y trazabilidad de operaciones

---

## 🖼️ Servicio de imágenes (público)

### `/api/images/products/{filename}`
- Servido dinámico de imágenes
- Soporte para visualización directa en navegador

---

## 📤 Exportación de reportes (ADMIN)

- PDF
- Excel
- CSV

Endpoints bajo `/api/reports/export/*`

---

## 👨‍💻 Autor

**José Alberto Méndez Domínguez**  
Ingeniería de Software  
📍 Santa Marta, Colombia  

---

