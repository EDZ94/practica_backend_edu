package es.ediae.master.programacion.gestionusuario.controller;

import es.ediae.master.programacion.gestionusuario.model.UsuarioModel;
import es.ediae.master.programacion.gestionusuario.service.IUsuarioService;
import es.ediae.master.programacion.gestionusuario.service.IGeneroService;
import es.ediae.master.programacion.gestionusuario.service.IPuestoTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController { 

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IPuestoTrabajoService puestoTrabajoService;

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        boolean valido = usuarioService.iniciarSesion(username, password);
        if (valido) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Login correcto"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("status", "error", "message", "Credenciales incorrectas"));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> obtenerUsuarios(
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        List<UsuarioModel> usuarios = usuarioService.obtenerUsuarios(nickUsuario, nickContrasena);
        if (usuarios == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> obtenerUsuario(
            @PathVariable Integer id,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        UsuarioModel usuario = usuarioService.obtenerUsuario(id, nickUsuario, nickContrasena);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(
            @RequestBody UsuarioModel model,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        UsuarioModel creado = usuarioService.crearUsuario(model, nickUsuario, nickContrasena);
        if (creado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: El nick ya existe o no tienes permisos"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer id, 
            @RequestBody UsuarioModel model,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        UsuarioModel actualizado = usuarioService.actualizarUsuario(id, model, nickUsuario, nickContrasena);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "No se pudo actualizar. ID no encontrado, nick duplicado o credenciales inválidas"));
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(
            @PathVariable Integer id,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        boolean eliminado = usuarioService.eliminarUsuario(id, nickUsuario, nickContrasena);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generos")
    public ResponseEntity<?> obtenerGeneros(
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        List<Map<String, Object>> generos = generoService.obtenerGeneros(nickUsuario, nickContrasena);
        if (generos == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(generos);
    }

    @GetMapping("/puestos")
    public ResponseEntity<?> obtenerPuestosDeTrabajo(
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        List<Map<String, Object>> puestos = puestoTrabajoService.obtenerPuestosDeTrabajo(nickUsuario, nickContrasena);
        if (puestos == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(puestos);
    }
}