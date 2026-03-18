package actividad.actividad.controller;

import actividad.actividad.model.Soporte;
import actividad.actividad.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/soporte")
@CrossOrigin(origins = "*")
public class SoporteController {
    
    @Autowired
    private SoporteService soporteService;
    
    // GET: Obtener todos los tickets
    @GetMapping
    public ResponseEntity<List<Soporte>> obtenerTodos() {
        List<Soporte> tickets = soporteService.obtenerTodos();
        return ResponseEntity.ok(tickets);
    }
    
    // GET: Obtener ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<Soporte> obtenerPorId(@PathVariable Long id) {
        Optional<Soporte> ticket = soporteService.obtenerPorId(id);
        return ticket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // GET: Obtener tickets por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Soporte>> obtenerPorCliente(@PathVariable Long clienteId) {
        List<Soporte> tickets = soporteService.obtenerPorCliente(clienteId);
        return ResponseEntity.ok(tickets);
    }
    
    // GET: Obtener tickets por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Soporte>> obtenerPorEstado(@PathVariable String estado) {
        try {
            Soporte.EstadoTicket estadoEnum = Soporte.EstadoTicket.valueOf(estado.toUpperCase());
            List<Soporte> tickets = soporteService.obtenerPorEstado(estadoEnum);
            return ResponseEntity.ok(tickets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // GET: Obtener tickets por prioridad
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<Soporte>> obtenerPorPrioridad(@PathVariable String prioridad) {
        try {
            Soporte.Prioridad prioridadEnum = Soporte.Prioridad.valueOf(prioridad.toUpperCase());
            List<Soporte> tickets = soporteService.obtenerPorPrioridad(prioridadEnum);
            return ResponseEntity.ok(tickets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // GET: Obtener tickets asignados a un agente
    @GetMapping("/agente/{agente}")
    public ResponseEntity<List<Soporte>> obtenerPorAgente(@PathVariable String agente) {
        List<Soporte> tickets = soporteService.obtenerPorAgente(agente);
        return ResponseEntity.ok(tickets);
    }
    
    // POST: Crear nuevo ticket
    @PostMapping
    public ResponseEntity<Soporte> crear(@RequestBody Soporte soporte) {
        Soporte ticketCreado = soporteService.crear(soporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCreado);
    }
    
    // PUT: Actualizar ticket
    @PutMapping("/{id}")
    public ResponseEntity<Soporte> actualizar(@PathVariable Long id, @RequestBody Soporte soporte) {
        Soporte ticketActualizado = soporteService.actualizar(id, soporte);
        if (ticketActualizado != null) {
            return ResponseEntity.ok(ticketActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    // PATCH: Asignar agente
    @PatchMapping("/{id}/asignar-agente")
    public ResponseEntity<Soporte> asignarAgente(@PathVariable Long id, @RequestBody AsignarAgenteRequest request) {
        Soporte ticket = soporteService.asignarAgente(id, request.getAgente());
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }
    
    // PATCH: Resolver ticket
    @PatchMapping("/{id}/resolver")
    public ResponseEntity<Soporte> resolverTicket(@PathVariable Long id, @RequestBody ResolverTicketRequest request) {
        Soporte ticket = soporteService.resolverTicket(id, request.getResolucion());
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }
    
    // PATCH: Cerrar ticket
    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<Soporte> cerrarTicket(@PathVariable Long id, @RequestBody CerrarTicketRequest request) {
        Soporte ticket = soporteService.cerrarTicket(id, request.getSatisfaccion());
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE: Eliminar ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (soporteService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Clases auxiliares
    public static class AsignarAgenteRequest {
        private String agente;
        
        public String getAgente() {
            return agente;
        }
        
        public void setAgente(String agente) {
            this.agente = agente;
        }
    }
    
    public static class ResolverTicketRequest {
        private String resolucion;
        
        public String getResolucion() {
            return resolucion;
        }
        
        public void setResolucion(String resolucion) {
            this.resolucion = resolucion;
        }
    }
    
    public static class CerrarTicketRequest {
        private Integer satisfaccion;
        
        public Integer getSatisfaccion() {
            return satisfaccion;
        }
        
        public void setSatisfaccion(Integer satisfaccion) {
            this.satisfaccion = satisfaccion;
        }
    }
}
