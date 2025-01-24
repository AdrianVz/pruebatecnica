package com.pruebaarkon.pruebatecnica.repository;

import com.pruebaarkon.pruebatecnica.model.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Evento.
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    /**
     * Verifica si existe un evento con un nombre específico, excluyendo el evento
     * con un identificador dado.
     *
     * @param nombre el nombre del evento a buscar.
     * @param id     el identificador del evento que se debe excluir de la búsqueda.
     * @return true si existe un evento con el mismo nombre y un ID diferente, false en caso contrario.
     */
    boolean existsByNombreAndIdNot(String nombre, Long id);

}
