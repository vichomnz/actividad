package actividad.actividad.controller;

import actividad.actividad.model.Pedido;
import actividad.actividad.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    // GET: Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    // GET: Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.obtenerPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // GET: Obtener pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.obtenerPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }
    
    // GET: Obtener pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPorEstado(@PathVariable String estado) {
        try {
            Pedido.EstadoPedido estadoEnum = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
            List<Pedido> pedidos = pedidoService.obtenerPorEstado(estadoEnum);
            return ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // GET: Obtener pedidos con pagos pendientes
    @GetMapping("/pagos/pendientes")
    public ResponseEntity<List<Pedido>> obtenerPagosPendientes() {
        List<Pedido> pedidos = pedidoService.obtenerPagosPendientes();
        return ResponseEntity.ok(pedidos);
    }
    
    // POST: Crear nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido pedidoCreado = pedidoService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCreado);
    }
    
    // PUT: Actualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.actualizar(id, pedido);
        if (pedidoActualizado != null) {
            return ResponseEntity.ok(pedidoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    // PATCH: Actualizar estado de pedido
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestBody UpdateEstadoRequest request) {
        try {
            Pedido.EstadoPedido estado = Pedido.EstadoPedido.valueOf(request.getEstado().toUpperCase());
            Pedido pedido = pedidoService.actualizarEstado(id, estado);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PATCH: Actualizar estado de pago
    @PatchMapping("/{id}/pago")
    public ResponseEntity<Pedido> actualizarEstadoPago(@PathVariable Long id, @RequestBody UpdateEstadoPagoRequest request) {
        try {
            Pedido.EstadoPago estadoPago = Pedido.EstadoPago.valueOf(request.getEstadoPago().toUpperCase());
            Pedido pedido = pedidoService.actualizarEstadoPago(id, estadoPago);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // DELETE: Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (pedidoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Clases auxiliares
    public static class UpdateEstadoRequest {
        private String estado;
        
        public String getEstado() {
            return estado;
        }
        
        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
    
    public static class UpdateEstadoPagoRequest {
        private String estadoPago;
        
        public String getEstadoPago() {
            return estadoPago;
        }
        
        public void setEstadoPago(String estadoPago) {
            this.estadoPago = estadoPago;
        }
    }
}
