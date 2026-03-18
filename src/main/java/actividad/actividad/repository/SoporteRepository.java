package actividad.actividad.repository;

import actividad.actividad.model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long> {
    Optional<Soporte> findByNumeroTicket(String numeroTicket);
    List<Soporte> findByClienteId(Long clienteId);
    List<Soporte> findByEstado(Soporte.EstadoTicket estado);
    List<Soporte> findByPrioridad(Soporte.Prioridad prioridad);
    List<Soporte> findByAgenteAsignado(String agente);
}
