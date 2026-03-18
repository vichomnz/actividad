package actividad.actividad.service;

import actividad.actividad.model.Producto;
import actividad.actividad.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    // Obtener todos los productos
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    // Obtener producto por ID
    public Optional<Producto> obtenerPorId(@NonNull Long id) {
        return productoRepository.findById(id);
    }
    
    // Crear nuevo producto
    public Producto crear(@NonNull Producto producto) {
        return productoRepository.save(producto);
    }
    
    // Actualizar producto
    public Producto actualizar(@NonNull Long id, @NonNull Producto productoActualizado) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setNombre(productoActualizado.getNombre());
            p.setDescripcion(productoActualizado.getDescripcion());
            p.setPrecio(productoActualizado.getPrecio());
            p.setStock(productoActualizado.getStock());
            p.setAlmacen(productoActualizado.getAlmacen());
            p.setSku(productoActualizado.getSku());
            p.setActivo(productoActualizado.getActivo());
            return productoRepository.save(p);
        }
        return null;
    }
    
    // Eliminar producto
    public boolean eliminar(@NonNull Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener productos por almacén
    public List<Producto> obtenerPorAlmacen(String almacen) {
        return productoRepository.findByAlmacen(almacen);
    }
    
    // Actualizar stock
    public Producto actualizarStock(@NonNull Long id, @NonNull Integer nuevoStock) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setStock(nuevoStock);
            return productoRepository.save(p);
        }
        return null;
    }
    
    // Obtener productos activos
    public List<Producto> obtenerActivos() {
        return productoRepository.findByActivoTrue();
    }
}
