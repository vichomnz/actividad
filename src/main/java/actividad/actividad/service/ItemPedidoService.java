package actividad.actividad.service;

import actividad.actividad.model.ItemPedido;
import actividad.actividad.model.Pedido;
import actividad.actividad.model.Producto;
import actividad.actividad.repository.ItemPedidoRepository;
import actividad.actividad.repository.PedidoRepository;
import actividad.actividad.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    // Obtener todos los items de un pedido
    public List<ItemPedido> obtenerPorPedido(@NonNull Long pedidoId) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
        if (pedido.isPresent()) {
            return itemPedidoRepository.findByPedido(pedido.get());
        }
        return List.of();
    }
    
    // Agregar producto al pedido
    public ItemPedido agregarProductoAlPedido(@NonNull Long pedidoId, @NonNull Long productoId, @NonNull Integer cantidad) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
        Optional<Producto> producto = productoRepository.findById(productoId);
        
        if (pedido.isPresent() && producto.isPresent()) {
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido.get());
            item.setProducto(producto.get());
            item.setCantidad(cantidad);
            item.setPrecioUnitario(producto.get().getPrecio());
            
            // Agregar a la lista del pedido
            pedido.get().getItems().add(item);
            
            // Actualizar monto total del pedido
            actualizarMontoTotal(pedido.get());
            
            return itemPedidoRepository.save(item);
        }
        return null;
    }
    
    // Eliminar item del pedido
    public boolean eliminarItemDelPedido(@NonNull Long itemId) {
        Optional<ItemPedido> item = itemPedidoRepository.findById(itemId);
        if (item.isPresent()) {
            Pedido pedido = item.get().getPedido();
            itemPedidoRepository.deleteById(itemId);
            actualizarMontoTotal(pedido);
            return true;
        }
        return false;
    }
    
    // Calcular y actualizar monto total del pedido
    private void actualizarMontoTotal(@NonNull Pedido pedido) {
        Double nuevoMonto = pedido.getItems().stream()
                .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                .sum();
        pedido.setMonto(nuevoMonto);
        pedidoRepository.save(pedido);
    }
    
    // Obtener un item específico
    public Optional<ItemPedido> obtenerItem(@NonNull Long itemId) {
        return itemPedidoRepository.findById(itemId);
    }
}
