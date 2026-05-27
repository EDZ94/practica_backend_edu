package es.ediae.master.programacion.gestionusuario.service;

import es.ediae.master.programacion.gestionusuario.model.UsuarioModel;
import java.util.List;

public interface IUsuarioService {
    
    // El login se queda igual porque solo necesita las credenciales de quien entra
    boolean iniciarSesion(String username, String password);
    
    // A todos estos les meto los dos RequestParam que me pide el ejercicio 2.3
    List<UsuarioModel> obtenerUsuarios(String nickUsuario, String nickContrasena);
    
    UsuarioModel obtenerUsuario(Integer id, String nickUsuario, String nickContrasena);
    
    UsuarioModel crearUsuario(UsuarioModel model, String nickUsuario, String nickContrasena);
    
    UsuarioModel actualizarUsuario(Integer id, UsuarioModel model, String nickUsuario, String nickContrasena);
    
    boolean eliminarUsuario(Integer id, String nickUsuario, String nickContrasena);
}