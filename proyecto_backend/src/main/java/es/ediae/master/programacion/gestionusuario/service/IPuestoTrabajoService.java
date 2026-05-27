package es.ediae.master.programacion.gestionusuario.service;

import java.util.List;
import java.util.Map;

public interface IPuestoTrabajoService {
    List<Map<String, Object>> obtenerPuestosDeTrabajo(String nickUsuario, String nickContrasena);
}