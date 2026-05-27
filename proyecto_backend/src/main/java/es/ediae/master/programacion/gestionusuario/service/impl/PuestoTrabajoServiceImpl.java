package es.ediae.master.programacion.gestionusuario.service.impl;

import es.ediae.master.programacion.gestionusuario.repository.UsuarioRepository;
import es.ediae.master.programacion.gestionusuario.service.IPuestoTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PuestoTrabajoServiceImpl implements IPuestoTrabajoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Map<String, Object>> obtenerPuestosDeTrabajo(String nickUsuario, String nickContrasena) {
        if (!usuarioRepository.existsByNickUsuarioAndContrasena(nickUsuario, nickContrasena)) {
            return null;
        }
        return List.of(
            Map.of("id", 1, "nombre", "Senior Project Manager"),
            Map.of("id", 2, "nombre", "Senior Architect"),
            Map.of("id", 3, "nombre", "Head Of Operations"),
            Map.of("id", 4, "nombre", "Automation Tester")
        );
    }
}