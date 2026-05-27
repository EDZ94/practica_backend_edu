package es.ediae.master.programacion.gestionusuario.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Esta es mi entidad principal de usuarios con todos sus campos del PDF
@Data
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nick_usuario", nullable = false, unique = true)
    private String nickUsuario;

    @Column(nullable = false)
    private String contrasena;

    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDateTime fechaHoraCreacion;

    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private GeneroEntity genero;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "hora_desayuno")
    private LocalTime horaDesayuno;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private PuestoTrabajoEntity puestoTrabajo;

    // Ejercicio 2.1: el campo esAdmin obligatorio
    @Column(name = "es_admin", nullable = false)
    private Boolean esAdmin = false;
}