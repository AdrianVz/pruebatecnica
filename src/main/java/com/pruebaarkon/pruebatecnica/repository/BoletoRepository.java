package com.pruebaarkon.pruebatecnica.repository;

import com.pruebaarkon.pruebatecnica.model.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Boleto.
 */
@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long> {


    /**
     * Busca todos los boletos asociados a un evento específico.
     *
     * @param eventoId el identificador del evento cuyos boletos se desean obtener.
     * @return una lista de boletos asociados al evento con el ID especificado.
     */
    List<Boleto> findByEventoId(Long eventoId);

    /**
     * Busca un boleto específico por código único.
     *
     * @param codigoBoleto el código único del boleto a buscar.
     * @return un optional que contiene el boleto si se encuentra.
     */
    Optional<Boleto> findByCodigoBoleto(String codigoBoleto);
}
