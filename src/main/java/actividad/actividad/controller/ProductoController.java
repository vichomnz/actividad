package actividad.actividad.controller;

import actividad.actividad.model.Producto;
import actividad.actividad.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    // GET: Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }
    
    // GET: Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // GET: Obtener productos por almacén
    @GetMapping("/almacen/{almacen}")
    public ResponseEntity<List<Producto>> obtenerPorAlmacen(@PathVariable String almacen) {
        List<Producto> productos = productoService.obtenerPorAlmacen(almacen);
        return ResponseEntity.ok(productos);
    }
    
    // GET: Obtener productos activos
    @GetMapping("/estado/activos")
    public ResponseEntity<List<Producto>> obtenerActivos() {
        List<Producto> productos = productoService.obtenerActivos();
        return ResponseEntity.ok(productos);
    }
    
    // POST: Crear nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto productoCreado = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }
    
    // PUT: Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizar(id, producto);
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    // PATCH: Actualizar stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable Long id, @RequestBody UpdateStockRequest request) {
        Producto producto = productoService.actualizarStock(id, request.getNuevoStock());
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE: Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Clase auxiliar para actualizar stock
    public static class UpdateStockRequest {
        private Integer nuevoStock;
        
        public Integer getNuevoStock() {
            return nuevoStock;
        }
        
        public void setNuevoStock(Integer nuevoStock) {
            this.nuevoStock = nuevoStock;
        }
    }
}
