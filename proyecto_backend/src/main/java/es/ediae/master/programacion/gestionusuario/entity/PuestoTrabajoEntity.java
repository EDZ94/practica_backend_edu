package es.ediae.master.programacion.gestionusuario.entity;
import jakarta.persistence.*;
import lombok.Data;

// Configuro el puesto de trabajo mapeado con mi tabla de la base de datos
@Data
@Entity
@Table(name = "puesto_trabajo")
public class PuestoTrabajoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;
}