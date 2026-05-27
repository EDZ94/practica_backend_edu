package es.ediae.master.programacion.gestionusuario.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List; // Importo la lista para las direcciones

// Utilizo Lombok para que me genere los métodos de transporte de datos de forma limpia 
@Data
public class UsuarioModel {
    
    private Integer id;
    private String nickUsuario;
    private String contrasena; // La necesito para validar el login en el controlador
    private LocalDateTime fechaHoraCreacion;
    private String nombre;
    private String primerApellido;
    private String segundoApellido; // Este puede ser null según el PDF 
    private LocalDate fechaNacimiento;
    private LocalTime horaDesayuno; // Este también es nullabl
    private Boolean esAdmin; // Añadido obligatorio 
    
    // En lugar de pasar las entidades completas de Género y Puesto, paso solo sus datos planos
    private Integer idGenero;
    private String nombreGenero;
    
    private Integer idPuesto;
    private String nombrePuesto;

    // CAMPOS EXTRA MÍOS PARA QUE CONECTE BIEN CON MI FRONTEND DE ANGULAR

    // Meto la lista de sus direcciones para poder gestionarlas en línea dentro del popup
    private List<DireccionModel> direcciones;

    // Uso estos dos campos para enviarle la tabla de direcciones al frontend y que sepa cuál es la principal y cuántas extra tiene
    private String direccionPrincipalName;
    private Integer contadorDireccionesExtra;
}