package actividad.actividad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido_items")
public class ItemPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonProperty("producto")
    private Producto producto;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Double precioUnitario;
    
    // Campo calculado: subtotal
    @Transient
    public Double getSubtotal() {
        return cantidad * precioUnitario;
    }
}
