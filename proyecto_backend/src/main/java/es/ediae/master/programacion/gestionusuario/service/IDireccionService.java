package es.ediae.master.programacion.gestionusuario.service;

import es.ediae.master.programacion.gestionusuario.model.DireccionModel;
import java.util.List;

public interface IDireccionService {
    
    List<DireccionModel> obtenerDireccionesPorUsuario(Integer idUsuario, String nickUsuario, String nickContrasena);
    
    DireccionModel obtenerDireccion(Integer id, String nickUsuario, String nickContrasena);
    
    DireccionModel crearDireccion(DireccionModel model, String nickUsuario, String nickContrasena);
    
    DireccionModel actualizarDireccion(Integer id, DireccionModel model, String nickUsuario, String nickContrasena);
    
    boolean eliminarDireccion(Integer id, String nickUsuario, String nickContrasena);
}