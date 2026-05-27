package es.ediae.master.programacion.gestionusuario.entity;
import jakarta.persistence.*;
import lombok.Data;

// Mapeo de la tabla dirección vinculándola con cada usuario
@Data
@Entity
@Table(name = "direccion")
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_calle", nullable = false)
    private String nombreCalle;

    @Column(name = "numero_calle")
    private Integer numeroCalle;

    // Relación de muchas direcciones para un solo usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "direccion_principal", nullable = false)
    private Boolean esPrincipal = false;
}