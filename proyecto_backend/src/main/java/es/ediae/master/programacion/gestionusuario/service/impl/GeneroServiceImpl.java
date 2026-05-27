package es.ediae.master.programacion.gestionusuario.service.impl;

import es.ediae.master.programacion.gestionusuario.repository.UsuarioRepository;
import es.ediae.master.programacion.gestionusuario.service.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GeneroServiceImpl implements IGeneroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Map<String, Object>> obtenerGeneros(String nickUsuario, String nickContrasena) {
        if (!usuarioRepository.existsByNickUsuarioAndContrasena(nickUsuario, nickContrasena)) {
            return null; // Ejercicio 2.3: Bloqueo si el emisor no es válido
        }
        return List.of(
            Map.of("id", 1, "nombre", "Hombre"),
            Map.of("id", 2, "nombre", "Mujer")
        );
    }
}