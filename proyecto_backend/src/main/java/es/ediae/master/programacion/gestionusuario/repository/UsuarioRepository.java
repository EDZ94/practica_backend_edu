package es.ediae.master.programacion.gestionusuario.repository;

import es.ediae.master.programacion.gestionusuario.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    // Para buscar un usuario en la base de datos a través de su nick único
    Optional<UsuarioEntity> findByNickUsuario(String nickUsuario);

boolean existsByNickUsuario(String nickUsuario);
boolean existsByNickUsuarioAndContrasena(String nickUsuario, String contrasena);
boolean existsByNickUsuarioAndIdNot(String nickUsuario, Integer id);

}