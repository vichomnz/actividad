package actividad.actividad.repository;

import actividad.actividad.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByNumeroOrden(String numeroOrden);
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    List<Pedido> findByEstadoPago(Pedido.EstadoPago estadoPago);
}
