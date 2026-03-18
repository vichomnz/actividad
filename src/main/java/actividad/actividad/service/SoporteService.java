package actividad.actividad.service;

import actividad.actividad.model.Soporte;
import actividad.actividad.repository.SoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SoporteService {
    
    @Autowired
    private SoporteRepository soporteRepository;
    
    // Obtener todos los tickets
    public List<Soporte> obtenerTodos() {
        return soporteRepository.findAll();
    }
    
    // Obtener ticket por ID
    public Optional<Soporte> obtenerPorId(@NonNull Long id) {
        return soporteRepository.findById(id);
    }
    
    // Crear nuevo ticket
    public Soporte crear(@NonNull Soporte soporte) {
        soporte.setNumeroTicket("TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        soporte.setFechaCreacion(LocalDateTime.now());
        soporte.setEstado(Soporte.EstadoTicket.ABIERTO);
        soporte.setPrioridad(soporte.getPrioridad() != null ? soporte.getPrioridad() : Soporte.Prioridad.MEDIA);
        return soporteRepository.save(soporte);
    }
    
    // Actualizar ticket
    public Soporte actualizar(@NonNull Long id, @NonNull Soporte soporteActualizado) {
        Optional<Soporte> soporte = soporteRepository.findById(id);
        if (soporte.isPresent()) {
            Soporte s = soporte.get();
            s.setClienteId(soporteActualizado.getClienteId());
            s.setAsunto(soporteActualizado.getAsunto());
            s.setDescripcion(soporteActualizado.getDescripcion());
            s.setPrioridad(soporteActualizado.getPrioridad());
            s.setEstado(soporteActualizado.getEstado());
            s.setAgenteAsignado(soporteActualizado.getAgenteAsignado());
            s.setResolucion(soporteActualizado.getResolucion());
            s.setSatisfaccionCliente(soporteActualizado.getSatisfaccionCliente());
            s.setFechaActualizacion(LocalDateTime.now());
            return soporteRepository.save(s);
        }
        return null;
    }
    
    // Eliminar ticket
    public boolean eliminar(@NonNull Long id) {
        if (soporteRepository.existsById(id)) {
            soporteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener tickets por cliente
    public List<Soporte> obtenerPorCliente(Long clienteId) {
        return soporteRepository.findByClienteId(clienteId);
    }
    
    // Obtener tickets por estado
    public List<Soporte> obtenerPorEstado(Soporte.EstadoTicket estado) {
        return soporteRepository.findByEstado(estado);
    }
    
    // Obtener tickets por prioridad
    public List<Soporte> obtenerPorPrioridad(Soporte.Prioridad prioridad) {
        return soporteRepository.findByPrioridad(prioridad);
    }
    
    // Obtener tickets asignados a un agente
    public List<Soporte> obtenerPorAgente(String agente) {
        return soporteRepository.findByAgenteAsignado(agente);
    }
    
    // Asignar ticket a agente
    public Soporte asignarAgente(@NonNull Long id, @NonNull String agente) {
        Optional<Soporte> soporte = soporteRepository.findById(id);
        if (soporte.isPresent()) {
            Soporte s = soporte.get();
            s.setAgenteAsignado(agente);
            s.setEstado(Soporte.EstadoTicket.EN_PROCESO);
            s.setFechaActualizacion(LocalDateTime.now());
            return soporteRepository.save(s);
        }
        return null;
    }
    
    // Resolver ticket
    public Soporte resolverTicket(@NonNull Long id, @NonNull String resolucion) {
        Optional<Soporte> soporte = soporteRepository.findById(id);
        if (soporte.isPresent()) {
            Soporte s = soporte.get();
            s.setResolucion(resolucion);
            s.setEstado(Soporte.EstadoTicket.RESUELTO);
            s.setFechaActualizacion(LocalDateTime.now());
            return soporteRepository.save(s);
        }
        return null;
    }
    
    // Cerrar ticket
    public Soporte cerrarTicket(@NonNull Long id, @NonNull Integer satisfaccion) {
        Optional<Soporte> soporte = soporteRepository.findById(id);
        if (soporte.isPresent()) {
            Soporte s = soporte.get();
            s.setEstado(Soporte.EstadoTicket.CERRADO);
            s.setSatisfaccionCliente(satisfaccion);
            s.setFechaCierre(LocalDateTime.now());
            s.setFechaActualizacion(LocalDateTime.now());
            return soporteRepository.save(s);
        }
        return null;
    }
}
