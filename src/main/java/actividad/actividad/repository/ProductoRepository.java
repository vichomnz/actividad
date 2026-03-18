package actividad.actividad.repository;

import actividad.actividad.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByAlmacen(String almacen);
    Optional<Producto> findBySku(String sku);
    List<Producto> findByActivoTrue();
}
