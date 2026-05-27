package es.ediae.master.programacion.gestionusuario.controller;

import es.ediae.master.programacion.gestionusuario.model.DireccionModel;
import es.ediae.master.programacion.gestionusuario.service.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
public class DireccionController {

    @Autowired
    private IDireccionService direccionService;

    // GET: http://localhost:8080/api/direcciones/usuario/{idUsuario}?nickUsuario=XXX&nickContraseña=YYY
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerDireccionesPorUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        List<DireccionModel> direcciones = direccionService.obtenerDireccionesPorUsuario(idUsuario, nickUsuario, nickContrasena);
        if (direcciones == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(direcciones);
    }

    // GET: http://localhost:8080/api/direcciones/{id}?nickUsuario=XXX&nickContraseña=YYY
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDireccion(
            @PathVariable Integer id,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        DireccionModel direccion = direccionService.obtenerDireccion(id, nickUsuario, nickContrasena);
        if (direccion == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(direccion);
    }

    // POST: http://localhost:8080/api/direcciones?nickUsuario=XXX&nickContraseña=YYY
    @PostMapping
    public ResponseEntity<?> crearDireccion(
            @RequestBody DireccionModel model,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        DireccionModel creado = direccionService.crearDireccion(model, nickUsuario, nickContrasena);
        if (creado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear la dirección. Usuario inválido o credenciales incorrectas.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT: http://localhost:8080/api/direcciones/{id}?nickUsuario=XXX&nickContraseña=YYY
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDireccion(
            @PathVariable Integer id, 
            @RequestBody DireccionModel model,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        DireccionModel actualizado = direccionService.actualizarDireccion(id, model, nickUsuario, nickContrasena);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // DELETE: http://localhost:8080/api/direcciones/{id}?nickUsuario=XXX&nickContraseña=YYY
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDireccion(
            @PathVariable Integer id,
            @RequestParam(value = "nickUsuario") String nickUsuario,
            @RequestParam(value = "nickContraseña") String nickContrasena) {
        
        boolean eliminado = direccionService.eliminarDireccion(id, nickUsuario, nickContrasena);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }
}