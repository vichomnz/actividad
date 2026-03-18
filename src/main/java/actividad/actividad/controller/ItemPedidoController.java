package actividad.actividad.controller;

import actividad.actividad.model.ItemPedido;
import actividad.actividad.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class ItemPedidoController {
    
    @Autowired
    private ItemPedidoService itemPedidoService;
    
    // GET: Obtener todos los items de un pedido
    @GetMapping("/{pedidoId}/items")
    public ResponseEntity<List<ItemPedido>> obtenerItemsDelPedido(@PathVariable Long pedidoId) {
        List<ItemPedido> items = itemPedidoService.obtenerPorPedido(pedidoId);
        return ResponseEntity.ok(items);
    }
    
    // GET: Obtener un item específico
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemPedido> obtenerItem(@PathVariable Long itemId) {
        Optional<ItemPedido> item = itemPedidoService.obtenerItem(itemId);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // POST: Agregar producto al pedido
    @PostMapping("/{pedidoId}/items")
    public ResponseEntity<ItemPedido> agregarProducto(@PathVariable Long pedidoId, 
                                                       @RequestBody AgregarProductoRequest request) {
        ItemPedido item = itemPedidoService.agregarProductoAlPedido(
            pedidoId, 
            request.getProductoId(), 
            request.getCantidad()
        );
        if (item != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        }
        return ResponseEntity.badRequest().build();
    }
    
    // DELETE: Eliminar producto del pedido
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long itemId) {
        if (itemPedidoService.eliminarItemDelPedido(itemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // DTO para agregar producto
    public static class AgregarProductoRequest {
        private Long productoId;
        private Integer cantidad;
        
        public Long getProductoId() {
            return productoId;
        }
        
        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }
        
        public Integer getCantidad() {
            return cantidad;
        }
        
        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}
