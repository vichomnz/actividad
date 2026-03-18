package actividad.actividad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets_soporte")
public class Soporte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String numeroTicket;
    
    @Column(nullable = false)
    private Long clienteId;
    
    @Column(nullable = false)
    private String asunto;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad; // BAJA, MEDIA, ALTA, CRITICA
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoTicket estado; // ABIERTO, EN_PROCESO, RESUELTO, CERRADO
    
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column
    private LocalDateTime fechaActualizacion;
    
    @Column
    private LocalDateTime fechaCierre;
    
    @Column
    private String agenteAsignado;
    
    @Column(columnDefinition = "TEXT")
    private String resolucion;
    
    @Column
    private Integer satisfaccionCliente; // 1-5 estrellas
    
    public enum Prioridad {
        BAJA, MEDIA, ALTA, CRITICA
    }
    
    public enum EstadoTicket {
        ABIERTO, EN_PROCESO, RESUELTO, CERRADO
    }
}
