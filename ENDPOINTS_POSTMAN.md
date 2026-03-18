# Documentación de Endpoints - 3 Microservicios

## Base URL
```
http://localhost:8080/api
```

---

## 🏪 MICROSERVICIO 1: PRODUCTOS (Inventario)

### GET - Obtener todos los productos
```
GET /productos
```
**Respuesta:**
```json
[
  {
    "id": 1,
    "nombre": "Laptop Dell",
    "descripcion": "Laptop high-end performance",
    "precio": 1299.99,
    "stock": 50,
    "almacen": "Almacen-Principal",
    "sku": "SKU-001",
    "activo": true
  }
]
```

### GET - Obtener producto por ID
```
GET /productos/{id}
```
**Ejemplo:**
```
GET /productos/1
```

### GET - Obtener productos por almacén
```
GET /productos/almacen/{almacen}
```
**Ejemplo:**
```
GET /productos/almacen/Almacen-Principal
```

### GET - Obtener productos activos
```
GET /productos/estado/activos
```

### POST - Crear nuevo producto
```
POST /productos
```
**Body:**
```json
{
  "nombre": "Laptop Dell",
  "descripcion": "Laptop high-end performance",
  "precio": 1299.99,
  "stock": 50,
  "almacen": "Almacen-Principal",
  "sku": "SKU-001",
  "activo": true
}
```

### PUT - Actualizar producto
```
PUT /productos/{id}
```
**Ejemplo:**
```
PUT /productos/1
```
**Body:**
```json
{
  "nombre": "Laptop Dell XPS",
  "descripcion": "Updated description",
  "precio": 1399.99,
  "stock": 45,
  "almacen": "Almacen-Principal",
  "sku": "SKU-001",
  "activo": true
}
```

### PATCH - Actualizar stock
```
PATCH /productos/{id}/stock
```
**Ejemplo:**
```
PATCH /productos/1/stock
```
**Body:**
```json
{
  "nuevoStock": 35
}
```

### DELETE - Eliminar producto
```
DELETE /productos/{id}
```
**Ejemplo:**
```
DELETE /productos/1
```

---

## 📦 MICROSERVICIO 2: PEDIDOS

### GET - Obtener todos los pedidos
```
GET /pedidos
```

### GET - Obtener pedido por ID
```
GET /pedidos/{id}
```

### GET - Obtener pedidos por cliente
```
GET /pedidos/cliente/{clienteId}
```
**Ejemplo:**
```
GET /pedidos/cliente/1
```

### GET - Obtener pedidos por estado
```
GET /pedidos/estado/{estado}
```
**Estados válidos:** PENDIENTE, CONFIRMADO, ENVIADO, ENTREGADO, CANCELADO

**Ejemplo:**
```
GET /pedidos/estado/PENDIENTE
```

### GET - Obtener pedidos con pagos pendientes
```
GET /pedidos/pagos/pendientes
```

### POST - Crear nuevo pedido
```
POST /pedidos
```
**Body:**
```json
{
  "clienteId": 1,
  "monto": 1299.99,
  "direccionEnvio": "Calle Principal 123, Apt 4B"
}
```
**Nota:** Se genera automáticamente:
- numeroOrden (UUID)
- estado: PENDIENTE
- estadoPago: PENDIENTE
- fechaCreacion

### PUT - Actualizar pedido
```
PUT /pedidos/{id}
```
**Body:**
```json
{
  "clienteId": 1,
  "monto": 1299.99,
  "estado": "CONFIRMADO",
  "estadoPago": "PROCESANDO",
  "direccionEnvio": "Calle Principal 123, Apt 4B",
  "numeroSeguimiento": "TRACK123456"
}
```

### PATCH - Actualizar estado de pedido
```
PATCH /pedidos/{id}/estado
```
**Body:**
```json
{
  "estado": "ENVIADO"
}
```

### PATCH - Actualizar estado de pago
```
PATCH /pedidos/{id}/pago
```
**Estados válidos:** PENDIENTE, PROCESANDO, PAGADO, RECHAZADO

**Body:**
```json
{
  "estadoPago": "PAGADO"
}
```

### DELETE - Eliminar pedido
```
DELETE /pedidos/{id}
```

---

## 🎧 MICROSERVICIO 3: SOPORTE (Servicio a Usuarios)

### GET - Obtener todos los tickets
```
GET /soporte
```

### GET - Obtener ticket por ID
```
GET /soporte/{id}
```

### GET - Obtener tickets por cliente
```
GET /soporte/cliente/{clienteId}
```

### GET - Obtener tickets por estado
```
GET /soporte/estado/{estado}
```
**Estados válidos:** ABIERTO, EN_PROCESO, RESUELTO, CERRADO

**Ejemplo:**
```
GET /soporte/estado/ABIERTO
```

### GET - Obtener tickets por prioridad
```
GET /soporte/prioridad/{prioridad}
```
**Prioridades válidas:** BAJA, MEDIA, ALTA, CRITICA

**Ejemplo:**
```
GET /soporte/prioridad/CRITICA
```

### GET - Obtener tickets por agente
```
GET /soporte/agente/{agente}
```

### POST - Crear nuevo ticket
```
POST /soporte
```
**Body:**
```json
{
  "clienteId": 1,
  "asunto": "Problema con pedido",
  "descripcion": "No he recibido mi pedido después de 5 días",
  "prioridad": "ALTA"
}
```
**Nota:** Se genera automáticamente:
- numeroTicket (TKT-XXXXXXXX)
- estado: ABIERTO
- fechaCreacion

### PUT - Actualizar ticket
```
PUT /soporte/{id}
```
**Body:**
```json
{
  "clienteId": 1,
  "asunto": "Problema con pedido",
  "descripcion": "No he recibido mi pedido después de 5 días",
  "prioridad": "ALTA",
  "estado": "EN_PROCESO",
  "agenteAsignado": "Juan Pérez",
  "resolucion": null,
  "satisfaccionCliente": null
}
```

### PATCH - Asignar agente
```
PATCH /soporte/{id}/asignar-agente
```
**Body:**
```json
{
  "agente": "Juan Pérez"
}
```

### PATCH - Resolver ticket
```
PATCH /soporte/{id}/resolver
```
**Body:**
```json
{
  "resolucion": "Se reenvió el paquete, será entregado en 2 días"
}
```

### PATCH - Cerrar ticket
```
PATCH /soporte/{id}/cerrar
```
**Body:**
```json
{
  "satisfaccion": 5
}
```
**Nota:** satisfaccion es de 1-5 estrellas

### DELETE - Eliminar ticket
```
DELETE /soporte/{id}
```

---

## 🔧 Notas Importantes

1. **CORS habilitado:** Todos los endpoints permiten CORS desde cualquier origen
2. **Timestamps:** Se generan automáticamente en creación y actualización
3. **IDs:** Se generan automáticamente con estrategia IDENTITY
4. **Estados y Enumeraciones:** Son case-insensitive en la ruta pero se validan contra valores válidos
5. **Respuestas de error:**
   - 404 Not Found: Recurso no existe
   - 400 Bad Request: Datos inválidos o enumeración incorrecta
   - 201 Created: Recurso creado exitosamente
   - 204 No Content: Recurso eliminado exitosamente

---

## 📊 Ejemplos en Postman

### Crear un producto
```
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "iPhone 14",
  "descripcion": "Smartphone Apple",
  "precio": 999.99,
  "stock": 100,
  "almacen": "Almacen-Centro",
  "sku": "SKU-IPHONE14",
  "activo": true
}
```

### Crear un pedido
```
POST http://localhost:8080/api/pedidos
Content-Type: application/json

{
  "clienteId": 1,
  "monto": 999.99,
  "direccionEnvio": "Calle 5ta Avenida 789"
}
```

### Crear un ticket de soporte
```
POST http://localhost:8080/api/soporte
Content-Type: application/json

{
  "clienteId": 1,
  "asunto": "Reembolso",
  "descripcion": "Solicito reembolso de mi compra",
  "prioridad": "ALTA"
}
```

### Actualizar estado de pedido
```
PATCH http://localhost:8080/api/pedidos/1/estado
Content-Type: application/json

{
  "estado": "ENVIADO"
}
```

---

## 📱 Configuración en application.properties

```properties
spring.application.name=actividad
spring.datasource.url=jdbc:mysql://localhost:3306/actividad_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
