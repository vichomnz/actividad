# Documentación de Endpoints - 3 Microservicios MEJORADO

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

### GET - Obtener producto por ID
```
GET /productos/{id}
```

### GET - Obtener productos por almacén
```
GET /productos/almacen/{almacen}
```

### GET - Obtener productos activos
```
GET /productos/estado/activos
```

### POST - Crear nuevo producto
```
POST /productos
Content-Type: application/json

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

{
  "nuevoStock": 35
}
```

### DELETE - Eliminar producto
```
DELETE /productos/{id}
```

---

## 📦 MICROSERVICIO 2: PEDIDOS (CON ITEMS)

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

### GET - Obtener pedidos por estado
```
GET /pedidos/estado/{estado}
```
**Estados válidos:** PENDIENTE, CONFIRMADO, ENVIADO, ENTREGADO, CANCELADO

### GET - Obtener pedidos con pagos pendientes
```
GET /pedidos/pagos/pendientes
```

### POST - Crear nuevo pedido
```
POST /pedidos

{
  "clienteId": 1,
  "monto": 0,
  "direccionEnvio": "Calle Principal 123, Apt 4B"
}
```
**Nota:** El monto se calcula automáticamente cuando agregas items

### PUT - Actualizar pedido
```
PUT /pedidos/{id}

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

{
  "estado": "ENVIADO"
}
```

### PATCH - Actualizar estado de pago
```
PATCH /pedidos/{id}/pago

{
  "estadoPago": "PAGADO"
}
```

### DELETE - Eliminar pedido
```
DELETE /pedidos/{id}
```

---

## 📦🛍️ NUEVO: ITEMS DEL PEDIDO (Productos en cada Pedido)

### GET - Obtener todos los items de un pedido
```
GET /pedidos/{pedidoId}/items
```
**Respuesta:**
```json
[
  {
    "id": 1,
    "pedido": { "id": 1, ... },
    "producto": { "id": 1, "nombre": "Laptop Dell", ... },
    "cantidad": 2,
    "precioUnitario": 1299.99,
    "subtotal": 2599.98
  }
]
```

### GET - Obtener un item específico
```
GET /pedidos/items/{itemId}
```

### POST - Agregar producto al pedido ⭐ IMPORTANTE
```
POST /pedidos/{pedidoId}/items

{
  "productoId": 1,
  "cantidad": 2
}
```
**Lo que hace:**
- Agrega el producto al pedido
- Calcula subtotal = cantidad × precioUnitario
- **Actualiza automáticamente el monto total del pedido**

**Respuesta:**
```json
{
  "id": 1,
  "pedido": { "id": 1, ... },
  "producto": { "id": 1, "nombre": "Laptop Dell", ... },
  "cantidad": 2,
  "precioUnitario": 1299.99,
  "subtotal": 2599.98
}
```

### DELETE - Eliminar producto del pedido
```
DELETE /pedidos/items/{itemId}
```
**Lo que hace:**
- Elimina el item
- Recalcula el monto total del pedido automáticamente

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

### GET - Obtener tickets por prioridad
```
GET /soporte/prioridad/{prioridad}
```
**Prioridades válidas:** BAJA, MEDIA, ALTA, CRITICA

### GET - Obtener tickets por agente
```
GET /soporte/agente/{agente}
```

### POST - Crear nuevo ticket
```
POST /soporte

{
  "clienteId": 1,
  "asunto": "Problema con pedido",
  "descripcion": "No he recibido mi pedido después de 5 días",
  "prioridad": "ALTA"
}
```

### PUT - Actualizar ticket
```
PUT /soporte/{id}

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

{
  "agente": "Juan Pérez"
}
```

### PATCH - Resolver ticket
```
PATCH /soporte/{id}/resolver

{
  "resolucion": "Se reenvió el paquete, será entregado en 2 días"
}
```

### PATCH - Cerrar ticket
```
PATCH /soporte/{id}/cerrar

{
  "satisfaccion": 5
}
```

### DELETE - Eliminar ticket
```
DELETE /soporte/{id}
```

---

## 📝 FLUJO DE EJEMPLO COMPLETO

### 1️⃣ Crear un producto
```bash
POST /api/productos
{
  "nombre": "iPhone 14",
  "descripcion": "Smartphone Apple",
  "precio": 999.99,
  "stock": 100,
  "almacen": "Almacen-Centro",
  "sku": "SKU-IPHONE14",
  "activo": true
}
# Respuesta: { "id": 1, ... }
```

### 2️⃣ Crear un pedido
```bash
POST /api/pedidos
{
  "clienteId": 1,
  "monto": 0,
  "direccionEnvio": "Calle 5ta Avenida 789"
}
# Respuesta: { "id": 1, "numeroOrden": "ABC12345", ... }
```

### 3️⃣ Agregar productos al pedido ⭐
```bash
POST /api/pedidos/1/items
{
  "productoId": 1,
  "cantidad": 2
}
# El monto del pedido se actualiza a: 1999.98
```

### 4️⃣ Ver el pedido completo con items
```bash
GET /api/pedidos/1
# Respuesta muestra:
# - pedido con monto: 1999.98
# - items: [{ producto, cantidad, subtotal }]
```

### 5️⃣ Actualizar estado del pedido
```bash
PATCH /api/pedidos/1/estado
{
  "estado": "CONFIRMADO"
}
```

### 6️⃣ Si hay problema, crear ticket de soporte
```bash
POST /api/soporte
{
  "clienteId": 1,
  "asunto": "Reembolso",
  "descripcion": "Necesito reembolso del pedido",
  "prioridad": "ALTA"
}
```

---

## 🔧 Notas Importantes

1. **CORS habilitado:** Todos los endpoints permiten CORS desde cualquier origen
2. **Timestamps:** Se generan automáticamente en creación y actualización
3. **IDs:** Se generan automáticamente con estrategia IDENTITY
4. **Monto del Pedido:** Se calcula automáticamente al agregar/eliminar items
5. **Cascada:** Eliminar un pedido elimina automáticamente sus items
6. **Subtotal:** Se calcula como cantidad × precioUnitario

---

## 📊 Estructura de Datos

### ItemPedido
```json
{
  "id": 1,
  "pedido": { "id": 1 },
  "producto": {
    "id": 1,
    "nombre": "iPhone 14",
    "precio": 999.99,
    ...
  },
  "cantidad": 2,
  "precioUnitario": 999.99,
  "subtotal": 1999.98
}
```

### Pedido (ahora con items)
```json
{
  "id": 1,
  "numeroOrden": "ABC12345",
  "clienteId": 1,
  "monto": 1999.98,
  "estado": "CONFIRMADO",
  "estadoPago": "PAGADO",
  "direccionEnvio": "Calle 5ta Avenida 789",
  "numeroSeguimiento": "TRACK123456",
  "items": [
    {
      "id": 1,
      "producto": { "nombre": "iPhone 14", ... },
      "cantidad": 2,
      "precioUnitario": 999.99,
      "subtotal": 1999.98
    }
  ]
}
```

---

## ✅ Resumen de Cambios

| Antes | Ahora |
|-------|-------|
| Pedido solo guardaba monto total | Pedido guarda monto + items con productos |
| No sabías qué compraron | Ves exactamente qué, cuánto y a qué precio |
| Monto era manual | Monto se calcula automáticamente |
| N/A | Puedes ver subtotal de cada item |
