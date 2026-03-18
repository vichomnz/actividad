package actividad.actividad.service;

import actividad.actividad.model.Pedido;
import actividad.actividad.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    // Obtener todos los pedidos
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }
    
    // Obtener pedido por ID
    public Optional<Pedido> obtenerPorId(@NonNull Long id) {
        return pedidoRepository.findById(id);
    }
    
    // Crear nuevo pedido
    public Pedido crear(@NonNull Pedido pedido) {
        pedido.setNumeroOrden(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
        pedido.setEstadoPago(Pedido.EstadoPago.PENDIENTE);
        return pedidoRepository.save(pedido);
    }
    
    // Actualizar pedido
    public Pedido actualizar(@NonNull Long id, @NonNull Pedido pedidoActualizado) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido p = pedido.get();
            p.setClienteId(pedidoActualizado.getClienteId());
            p.setMonto(pedidoActualizado.getMonto());
            p.setEstado(pedidoActualizado.getEstado());
            p.setEstadoPago(pedidoActualizado.getEstadoPago());
            p.setDireccionEnvio(pedidoActualizado.getDireccionEnvio());
            p.setNumeroSeguimiento(pedidoActualizado.getNumeroSeguimiento());
            p.setFechaActualizacion(LocalDateTime.now());
            return pedidoRepository.save(p);
        }
        return null;
    }
    
    // Eliminar pedido
    public boolean eliminar(@NonNull Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener pedidos por cliente
    public List<Pedido> obtenerPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    
    // Actualizar estado de pedido
    public Pedido actualizarEstado(@NonNull Long id, @NonNull Pedido.EstadoPedido nuevoEstado) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido p = pedido.get();
            p.setEstado(nuevoEstado);
            p.setFechaActualizacion(LocalDateTime.now());
            return pedidoRepository.save(p);
        }
        return null;
    }
    
    // Actualizar estado de pago
    public Pedido actualizarEstadoPago(@NonNull Long id, @NonNull Pedido.EstadoPago nuevoEstadoPago) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido p = pedido.get();
            p.setEstadoPago(nuevoEstadoPago);
            p.setFechaActualizacion(LocalDateTime.now());
            return pedidoRepository.save(p);
        }
        return null;
    }
    
    // Obtener pedidos por estado
    public List<Pedido> obtenerPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
    // Obtener pedidos con pagos pendientes
    public List<Pedido> obtenerPagosPendientes() {
        return pedidoRepository.findByEstadoPago(Pedido.EstadoPago.PENDIENTE);
    }
}
