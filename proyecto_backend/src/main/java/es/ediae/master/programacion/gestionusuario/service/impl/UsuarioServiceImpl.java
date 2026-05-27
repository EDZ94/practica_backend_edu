package es.ediae.master.programacion.gestionusuario.service.impl;

import es.ediae.master.programacion.gestionusuario.entity.UsuarioEntity;
import es.ediae.master.programacion.gestionusuario.entity.GeneroEntity;
import es.ediae.master.programacion.gestionusuario.entity.PuestoTrabajoEntity;
import es.ediae.master.programacion.gestionusuario.model.UsuarioModel;
import es.ediae.master.programacion.gestionusuario.repository.UsuarioRepository;
import es.ediae.master.programacion.gestionusuario.service.IUsuarioService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ejercicio 2.3: Método auxiliar para comprobar si las credenciales de quien opera son válidas
    private boolean validarAcceso(String nick, String pass) {
        return usuarioRepository.existsByNickUsuarioAndContrasena(nick, pass);
    }

    @Override
    public boolean iniciarSesion(String username, String password) {
        return usuarioRepository.existsByNickUsuarioAndContrasena(username, password);
    }

    @Override
    public List<UsuarioModel> obtenerUsuarios(String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }
        
        // Mapeo toda la lista usando el convertidor de abajo para rellenar todas las columnas
        return usuarioRepository.findAll().stream()
                .map(this::convertirAModelo)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioModel obtenerUsuario(Integer id, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }
        
        return usuarioRepository.findById(id)
                .map(this::convertirAModelo)
                .orElse(null);
    }

    @Override
    public UsuarioModel crearUsuario(UsuarioModel model, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }

        // Ejercicio 2.2: Evito nicks duplicados en el sistema
        if (usuarioRepository.existsByNickUsuario(model.getNickUsuario())) {
            return null; 
        }

        UsuarioEntity entidad = new UsuarioEntity();
        convertirAEntidad(model, entidad);
        
        // Requisito de la pág. 3: La fecha de creación se asigna automáticamente y no es editable
        entidad.setFechaHoraCreacion(LocalDateTime.now());

        // Guardado real y físico en MySQL
        UsuarioEntity guardado = usuarioRepository.save(entidad);
        return convertirAModelo(guardado); 
    }

    @Override
    public UsuarioModel actualizarUsuario(Integer id, UsuarioModel model, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }

        // Ejercicio 2.2: Evito duplicar nicks con terceros al editar
        if (usuarioRepository.existsByNickUsuarioAndIdNot(model.getNickUsuario(), id)) {
            return null; 
        }

        return usuarioRepository.findById(id)
                .map(entidadExistente -> {
                    convertirAEntidad(model, entidadExistente);
                    // Mantenemos la fecha original de creación intacta
                    UsuarioEntity actualizado = usuarioRepository.save(entidadExistente);
                    return convertirAModelo(actualizado);
                }).orElse(null);
    }

    @Override
    public boolean eliminarUsuario(Integer id, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return false;
        }
        
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        
        usuarioRepository.deleteById(id);
        return true;
    }


    private UsuarioModel convertirAModelo(UsuarioEntity entidad) {
        UsuarioModel m = new UsuarioModel();
        m.setId(entidad.getId());
        m.setNickUsuario(entidad.getNickUsuario());
        m.setContrasena(entidad.getContrasena());
        m.setFechaHoraCreacion(entidad.getFechaHoraCreacion());
        m.setNombre(entidad.getNombre());
        m.setPrimerApellido(entidad.getPrimerApellido());
        m.setSegundoApellido(entidad.getSegundoApellido());
        m.setFechaNacimiento(entidad.getFechaNacimiento());
        m.setHoraDesayuno(entidad.getHoraDesayuno());
        m.setEsAdmin(entidad.getEsAdmin());

        // Mapeo manual de los datos planos de Género y Puesto para no exponer las entidades completas
        if (entidad.getGenero() != null) {
            m.setIdGenero(entidad.getGenero().getId());
            m.setNombreGenero(entidad.getGenero().getNombre());
        }

        if (entidad.getPuestoTrabajo() != null) {
            m.setIdPuesto(entidad.getPuestoTrabajo().getId());
            m.setNombrePuesto(entidad.getPuestoTrabajo().getNombre());
        }

        return m;
    }

    private void convertirAEntidad(UsuarioModel m, UsuarioEntity entidad) {
        entidad.setNickUsuario(m.getNickUsuario());
        entidad.setContrasena(m.getContrasena());
        entidad.setNombre(m.getNombre());
        entidad.setPrimerApellido(m.getPrimerApellido());
        entidad.setSegundoApellido(m.getSegundoApellido());
        entidad.setFechaNacimiento(m.getFechaNacimiento());
        entidad.setHoraDesayuno(m.getHoraDesayuno());
        entidad.setEsAdmin(m.getEsAdmin() != null ? m.getEsAdmin() : false);

        // Mapeo manual de los datos planos de Género y Puesto para asignar las relaciones sin exponer las entidades completas
        if (m.getIdGenero() != null) {
            GeneroEntity g = new GeneroEntity();
            g.setId(m.getIdGenero());
            entidad.setGenero(g);
        }

        if (m.getIdPuesto() != null) {
            PuestoTrabajoEntity p = new PuestoTrabajoEntity();
            p.setId(m.getIdPuesto());
            entidad.setPuestoTrabajo(p);
        } else {
            entidad.setPuestoTrabajo(null);
        }
    }
}