package es.ediae.master.programacion.gestionusuario.repository;

import es.ediae.master.programacion.gestionusuario.entity.PuestoTrabajoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuestoTrabajoRepository extends JpaRepository<PuestoTrabajoEntity, Integer> {
    // Este método me servirá para comprobar que no se repitan nombres de puestos
    Optional<PuestoTrabajoEntity> findByNombre(String nombre);
}