package es.ediae.master.programacion.gestionusuario.service;

import java.util.List;
import java.util.Map;

public interface IGeneroService {
    List<Map<String, Object>> obtenerGeneros(String nickUsuario, String nickContrasena);
}