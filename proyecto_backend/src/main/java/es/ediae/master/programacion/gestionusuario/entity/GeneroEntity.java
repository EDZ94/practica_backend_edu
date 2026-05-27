package es.ediae.master.programacion.gestionusuario.entity;

import jakarta.persistence.*;
import lombok.Data;

// Uso Lombok para olvidarme de escribir los getters y setters a mano
@Data
@Entity
@Table(name = "genero")
public class GeneroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;
}