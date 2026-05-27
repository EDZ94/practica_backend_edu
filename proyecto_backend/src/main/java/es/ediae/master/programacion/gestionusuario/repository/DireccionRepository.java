package es.ediae.master.programacion.gestionusuario.repository;

import es.ediae.master.programacion.gestionusuario.entity.DireccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionEntity, Integer> {
    // Busca todas las direcciones de un usuario concreto
    List<DireccionEntity> findByUsuarioId(Integer idUsuario);

    // Busca si el usuario ya tiene alguna dirección marcada como principal
    List<DireccionEntity> findByUsuarioIdAndEsPrincipalTrue(Integer idUsuario);
}