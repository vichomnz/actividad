# 🚀 3 Microservicios - Sistema de E-Commerce

Solución de 3 microservicios independientes en Spring Boot para gestionar Inventario, Pedidos y Soporte al cliente.

## 📋 Descripción

Esta aplicación implementa 3 microservicios para resolver los problemas principales de un sistema e-commerce:

### 1. **PRODUCTOS (Inventario)** 🏪
- Sincronización eficiente entre múltiples almacenes
- Actualizaciones en tiempo real de stock
- Disponibilidad sincronizada entre catálogo e inventario
- Escalable para nuevos almacenes

**Endpoints:** `/api/productos`

### 2. **PEDIDOS** 📦
- Procesamiento optimizado de pedidos
- Gestión de estados de pedido
- Control de pagos
- Seguimiento de envíos
- Optimizado para picos de demanda

**Endpoints:** `/api/pedidos`

### 3. **SOPORTE** 🎧
- Sistema de tickets para soporte al cliente
- Asignación de agentes
- Priorización de problemas
- Seguimiento de satisfacción del cliente
- Personalización y recomendaciones

**Endpoints:** `/api/soporte`

---

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.11**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **Maven**

---

## 📦 Estructura del Proyecto

```
src/main/java/actividad/actividad/
├── model/
│   ├── Producto.java
│   ├── Pedido.java
│   └── Soporte.java
├── repository/
│   ├── ProductoRepository.java
│   ├── PedidoRepository.java
│   └── SoporteRepository.java
├── service/
│   ├── ProductoService.java
│   ├── PedidoService.java
│   └── SoporteService.java
├── controller/
│   ├── ProductoController.java
│   ├── PedidoController.java
│   └── SoporteController.java
├── config/
│   └── CorsConfig.java
└── Application.java
```

---

## 🔧 Configuración

### 1. Base de Datos

Crea una base de datos MySQL:

```sql
CREATE DATABASE actividad_db;
```

### 2. Configurar `application.properties`

El archivo ya está configurado con los siguientes valores por defecto:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/actividad_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

**Modifica los valores según tu entorno:**
- URL de la base de datos
- Usuario MySQL
- Contraseña MySQL

### 3. Instalar Dependencias

```bash
mvn clean install
```

---

## ▶️ Ejecutar la Aplicación

### Opción 1: Maven

```bash
mvn spring-boot:run
```

### Opción 2: Ejecutar JAR

```bash
mvn clean package
java -jar target/actividad-0.0.1-SNAPSHOT.jar
```

### Opción 3: IDE (IntelliJ/Eclipse)

1. Abre la clase `Application.java`
2. Haz clic en el botón "Run" (▶️)

---

## 📚 Documentación de Endpoints

Ver el archivo **`ENDPOINTS_POSTMAN.md`** para la documentación completa de todos los endpoints con ejemplos.

### URLs Rápidas

```
GET  /api/productos              - Obtener todos los productos
POST /api/productos              - Crear producto

GET  /api/pedidos                - Obtener todos los pedidos
POST /api/pedidos                - Crear pedido

GET  /api/soporte                - Obtener todos los tickets
POST /api/soporte                - Crear ticket
```

---

## 🧪 Usar en Postman

### Importar Endpoints

1. Abre **Postman**
2. Crea una **New Collection**
3. Agrega nuevas requests usando los endpoints del archivo `ENDPOINTS_POSTMAN.md`

### Configurar Variables

Añade una variable global en Postman:

```
Key: base_url
Value: http://localhost:8080/api
```

Luego usa `{{base_url}}/productos` en lugar de la URL completa.

### Ejemplo de Request

```
POST {{base_url}}/productos
Content-Type: application/json

{
  "nombre": "Laptop",
  "descripcion": "High-end laptop",
  "precio": 1299.99,
  "stock": 50,
  "almacen": "Almacen-Principal",
  "sku": "SKU-001",
  "activo": true
}
```

---

## 📊 Modelos de Datos

### Producto
```json
{
  "id": 1,
  "nombre": "string",
  "descripcion": "string",
  "precio": 0.0,
  "stock": 0,
  "almacen": "string",
  "sku": "string",
  "activo": true
}
```

### Pedido
```json
{
  "id": 1,
  "numeroOrden": "string",
  "clienteId": 0,
  "monto": 0.0,
  "estado": "PENDIENTE|CONFIRMADO|ENVIADO|ENTREGADO|CANCELADO",
  "estadoPago": "PENDIENTE|PROCESANDO|PAGADO|RECHAZADO",
  "direccionEnvio": "string",
  "fechaCreacion": "2024-03-17T10:30:00",
  "fechaActualizacion": "2024-03-17T10:30:00",
  "numeroSeguimiento": "string"
}
```

### Soporte (Ticket)
```json
{
  "id": 1,
  "numeroTicket": "TKT-XXXXXXXX",
  "clienteId": 0,
  "asunto": "string",
  "descripcion": "string",
  "prioridad": "BAJA|MEDIA|ALTA|CRITICA",
  "estado": "ABIERTO|EN_PROCESO|RESUELTO|CERRADO",
  "fechaCreacion": "2024-03-17T10:30:00",
  "fechaActualizacion": "2024-03-17T10:30:00",
  "fechaCierre": "2024-03-17T10:30:00",
  "agenteAsignado": "string",
  "resolucion": "string",
  "satisfaccionCliente": 0
}
```

---

## 🔄 Estados y Enumeraciones

### Estados de Pedido
- `PENDIENTE` - Recién creado
- `CONFIRMADO` - Pago confirmado
- `ENVIADO` - En camino
- `ENTREGADO` - Entregado al cliente
- `CANCELADO` - Pedido cancelado

### Estados de Pago
- `PENDIENTE` - Esperando pago
- `PROCESANDO` - Procesando pago
- `PAGADO` - Pago completado
- `RECHAZADO` - Pago rechazado

### Estados de Ticket
- `ABIERTO` - Nuevo ticket
- `EN_PROCESO` - Siendo atendido
- `RESUELTO` - Problema resuelto
- `CERRADO` - Ticket cerrado

### Prioridades
- `BAJA` - Consulta general
- `MEDIA` - Problema normal
- `ALTA` - Problema urgente
- `CRITICA` - Problema crítico

---

## 💡 Casos de Uso

### Escenario 1: Crear Producto y Pedido

```bash
# 1. Crear producto
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Laptop","descripcion":"High-end","precio":1299.99,"stock":50,"almacen":"Almacen-Principal","sku":"SKU-001","activo":true}'

# 2. Crear pedido
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"monto":1299.99,"direccionEnvio":"Calle 123"}'

# 3. Actualizar estado del pedido
curl -X PATCH http://localhost:8080/api/pedidos/1/estado \
  -H "Content-Type: application/json" \
  -d '{"estado":"CONFIRMADO"}'
```

### Escenario 2: Crear Ticket de Soporte

```bash
# 1. Crear ticket
curl -X POST http://localhost:8080/api/soporte \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"asunto":"Reembolso","descripcion":"Solicito reembolso","prioridad":"ALTA"}'

# 2. Asignar agente
curl -X PATCH http://localhost:8080/api/soporte/1/asignar-agente \
  -H "Content-Type: application/json" \
  -d '{"agente":"Juan Pérez"}'

# 3. Resolver ticket
curl -X PATCH http://localhost:8080/api/soporte/1/resolver \
  -H "Content-Type: application/json" \
  -d '{"resolucion":"Reembolso procesado"}'

# 4. Cerrar ticket
curl -X PATCH http://localhost:8080/api/soporte/1/cerrar \
  -H "Content-Type: application/json" \
  -d '{"satisfaccion":5}'
```

---

## 🐛 Troubleshooting

### Error: "Cannot connect to database"
- Verifica que MySQL está corriendo
- Comprueba las credenciales en `application.properties`
- Verifica que la base de datos `actividad_db` existe

### Error: "Port 8080 already in use"
Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Error: "No tables found"
Asegúrate de que `spring.jpa.hibernate.ddl-auto=update` está configurado. Las tablas se crearán automáticamente.

---

## 📝 Logs

Los logs se mostrarán en la consola durante la ejecución. Para cambiar el nivel de logs, modifica en `application.properties`:

```properties
logging.level.root=INFO
logging.level.actividad.actividad=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

---

## 🚀 Próximos Pasos

Para expandir esta solución:

1. **Autenticación** - Agregar Spring Security
2. **Validación** - Agregar validaciones con `@Valid`
3. **DTOs** - Crear Data Transfer Objects
4. **Excepciones** - Global Exception Handler
5. **Tests** - Agregar tests unitarios e integración
6. **Docker** - Containerizar la aplicación
7. **API Docs** - Agregar Swagger/OpenAPI
8. **Cache** - Implementar Redis
9. **Mensajería** - RabbitMQ para comunicación entre servicios
10. **Monitoring** - Actuator y Prometheus

---

## 📞 Soporte

Para preguntas o problemas, revisa la documentación en `ENDPOINTS_POSTMAN.md` o ajusta los valores en `application.properties`.

---

## 📄 Licencia

Este proyecto es de código abierto y está disponible para uso educativo y comercial.
