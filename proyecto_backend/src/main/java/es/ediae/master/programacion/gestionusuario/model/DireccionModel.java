package es.ediae.master.programacion.gestionusuario.model;

import lombok.Data;

@Data
public class DireccionModel {
    private Integer id;
    private String nombreCalle;
    private Integer numeroCalle;
    private Integer idUsuario; // Solo paso el ID del usuario dueño de la dirección
    private Boolean esPrincipal;
}