package actividad.actividad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String numeroOrden;
    
    @Column(nullable = false)
    private Long clienteId;
    
    @Column(nullable = false)
    private Double monto;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado; // PENDIENTE, CONFIRMADO, ENVIADO, ENTREGADO, CANCELADO
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago; // PENDIENTE, PROCESANDO, PAGADO, RECHAZADO
    
    @Column(nullable = false)
    private String direccionEnvio;
    
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column
    private LocalDateTime fechaActualizacion;
    
    @Column
    private String numeroSeguimiento;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items = new ArrayList<>();
    
    public enum EstadoPedido {
        PENDIENTE, CONFIRMADO, ENVIADO, ENTREGADO, CANCELADO
    }
    
    public enum EstadoPago {
        PENDIENTE, PROCESANDO, PAGADO, RECHAZADO
    }
}
