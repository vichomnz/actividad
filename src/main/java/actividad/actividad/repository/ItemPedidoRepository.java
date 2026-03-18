package actividad.actividad.repository;

import actividad.actividad.model.ItemPedido;
import actividad.actividad.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
    void deleteByPedido(Pedido pedido);
}
