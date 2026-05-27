package es.ediae.master.programacion.gestionusuario.service.impl;

import es.ediae.master.programacion.gestionusuario.entity.DireccionEntity;
import es.ediae.master.programacion.gestionusuario.entity.UsuarioEntity;
import es.ediae.master.programacion.gestionusuario.model.DireccionModel;
import es.ediae.master.programacion.gestionusuario.repository.DireccionRepository;
import es.ediae.master.programacion.gestionusuario.repository.UsuarioRepository;
import es.ediae.master.programacion.gestionusuario.service.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DireccionServiceImpl implements IDireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private boolean validarAcceso(String nick, String pass) {
        return usuarioRepository.existsByNickUsuarioAndContrasena(nick, pass);
    }

    @Override
    public List<DireccionModel> obtenerDireccionesPorUsuario(Integer idUsuario, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null; 
        }
        
        return direccionRepository.findByUsuarioId(idUsuario).stream()
                .map(this::convertirAModelo)
                .collect(Collectors.toList());
    }

    @Override
    public DireccionModel obtenerDireccion(Integer id, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }
        
        return direccionRepository.findById(id)
                .map(this::convertirAModelo)
                .orElse(null);
    }

    @Override
    @Transactional
    public DireccionModel crearDireccion(DireccionModel model, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }

        if (model.getEsPrincipal() != null && model.getEsPrincipal()) {
            desmarcarPrincipalesAnteriores(model.getIdUsuario());
        }

        DireccionEntity entidad = new DireccionEntity();
        convertirAEntidad(model, entidad);

        DireccionEntity guardada = direccionRepository.save(entidad);
        return convertirAModelo(guardada);
    }

    @Override
    @Transactional
    public DireccionModel actualizarDireccion(Integer id, DireccionModel model, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return null;
        }

        return direccionRepository.findById(id)
                .map(entidadExistente -> {
                    if (model.getEsPrincipal() != null && model.getEsPrincipal()) {
                        desmarcarPrincipalesAnteriores(model.getIdUsuario());
                    }
                    
                    convertirAEntidad(model, entidadExistente);
                    DireccionEntity actualizada = direccionRepository.save(entidadExistente);
                    return convertirAModelo(actualizada);
                }).orElse(null);
    }

    @Override
    public boolean eliminarDireccion(Integer id, String nickUsuario, String nickContrasena) {
        if (!validarAcceso(nickUsuario, nickContrasena)) {
            return false;
        }
        
        if (!direccionRepository.existsById(id)) {
            return false;
        }
        
        direccionRepository.deleteById(id);
        return true;
    }

    private void desmarcarPrincipalesAnteriores(Integer idUsuario) {
        List<DireccionEntity> activas = direccionRepository.findByUsuarioIdAndEsPrincipalTrue(idUsuario);
        for (DireccionEntity dir : activas) {
            dir.setEsPrincipal(false);
            direccionRepository.save(dir);
        }
    }

    private DireccionModel convertirAModelo(DireccionEntity entidad) {
        DireccionModel m = new DireccionModel();
        m.setId(entidad.getId());
        m.setNombreCalle(entidad.getNombreCalle());
        m.setNumeroCalle(entidad.getNumeroCalle());
        m.setEsPrincipal(entidad.getEsPrincipal());
        if (entidad.getUsuario() != null) {
            m.setIdUsuario(entidad.getUsuario().getId());
        }
        return m;
    }

    private void construirUsuarioProxy(Integer idUsuario, DireccionEntity entidad) {
        UsuarioEntity u = new UsuarioEntity();
        u.setId(idUsuario);
        entidad.setUsuario(u);
    }

    private void convertirAEntidad(DireccionModel m, DireccionEntity entidad) {
        entidad.setNombreCalle(m.getNombreCalle());
        entidad.setNumeroCalle(m.getNumeroCalle());
        entidad.setEsPrincipal(m.getEsPrincipal() != null ? m.getEsPrincipal() : false);
        if (m.getIdUsuario() != null) {
            construirUsuarioProxy(m.getIdUsuario(), entidad);
        }
    }
}