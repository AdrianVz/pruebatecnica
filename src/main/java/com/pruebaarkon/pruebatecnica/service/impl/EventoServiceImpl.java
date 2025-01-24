package com.pruebaarkon.pruebatecnica.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebaarkon.pruebatecnica.exception.EventoException;
import com.pruebaarkon.pruebatecnica.model.dto.DetalleEventoDTO;
import com.pruebaarkon.pruebatecnica.model.dto.InfoBoletoDTO;
import com.pruebaarkon.pruebatecnica.model.entity.Boleto;
import com.pruebaarkon.pruebatecnica.model.entity.Evento;
import com.pruebaarkon.pruebatecnica.repository.BoletoRepository;
import com.pruebaarkon.pruebatecnica.repository.EventoRepository;
import com.pruebaarkon.pruebatecnica.service.interfaces.EventoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private BoletoRepository boletoRepository;

    /**
     * Crea un nuevo evento después de validar sus datos.
     *
     * @param evento el evento a crear
     * @return el evento creado
     * @throws EventoException si los datos del evento no son válidos
     */
    public Evento crearEvento(Evento evento) {
        validarEvento(evento);
        return eventoRepository.save(evento);
    }

    /**
     * Actualiza los detalles de un evento existente.
     *
     * @param id el ID del evento a actualizar
     * @param detallesEvento los nuevos detalles del evento
     * @return una respuesta HTTP indicando el resultado de la operación
     */
    public ResponseEntity<String> actualizarEvento(Long id, Evento detallesEvento) {
        try {
            Evento evento = eventoRepository.findById(id)
                    .orElseThrow(() -> new EventoException("Evento no encontrado"));

            validarActualizacionEvento(evento, detallesEvento);

            evento.setNombre(detallesEvento.getNombre());
            evento.setFechaInicio(detallesEvento.getFechaInicio());
            evento.setFechaFin(detallesEvento.getFechaFin());
            evento.setTotalBoletos(detallesEvento.getTotalBoletos());

            eventoRepository.save(evento);
            return ResponseEntity.ok("Evento actualizado correctamente");
        } catch (EventoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Elimina un evento si cumple con las condiciones necesarias.
     *
     * @param id el ID del evento a eliminar
     * @return una respuesta HTTP indicando el resultado de la operación
     */
    public ResponseEntity<String> eliminarEvento(Long id) {
        try {
            Evento evento = eventoRepository.findById(id)
                    .orElseThrow(() -> new EventoException("Evento no encontrado"));

            if (evento.getFechaFin().isAfter(LocalDateTime.now())) {
                throw new EventoException("No se puede eliminar un evento que no ha finalizado");
            }

            if (evento.getBoletosVendidos() > 0) {
                throw new EventoException("No se puede eliminar un evento que tiene boletos vendidos");
            }

            eventoRepository.delete(evento);
            return ResponseEntity.ok("Evento eliminado correctamente");
        } catch (EventoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * Vende un boleto para un evento específico.
     *
     * @param eventoId el ID del evento para el cual se vende el boleto
     * @return un objeto InfoBoletoDTO con la información del boleto vendido
     */
    public InfoBoletoDTO venderBoleto(Long eventoId) {
        try {
            Evento evento = eventoRepository.findById(eventoId)
                    .orElseThrow(() -> new EventoException("Evento no encontrado"));

            long boletosVendidos = boletoRepository.findByEventoId(eventoId).size();
            if (boletosVendidos >= evento.getTotalBoletos()) {
                throw new EventoException("No hay boletos disponibles");
            }

            Boleto boleto = new Boleto();
            boleto.setEvento(evento);
            boleto.setCodigoBoleto(generarCodigoBoleto());
            boleto.setFechaVenta(LocalDateTime.now());

            evento.setBoletosVendidos(evento.getBoletosVendidos() + 1);
            boletoRepository.save(boleto);

            return new InfoBoletoDTO("Boleto vendido correctamente", boleto.getCodigoBoleto(), boleto.getEvento().getNombre());
        } catch (EventoException e) {
            log.error("Error al vender boleto: {}", e.getMessage());
            return new InfoBoletoDTO("Error al vender boleto: " + e.getMessage(), null, null);
        } catch (Exception e) {
            log.error("Error inesperado al vender boleto", e);
            return new InfoBoletoDTO("Error inesperado al vender boleto", null, null);
        }
    }

    /**
     * Canjea un boleto utilizando su código.
     *
     * @param codigoBoleto el código del boleto a canjear
     * @return una respuesta HTTP indicando el resultado de la operación
     */
    public ResponseEntity<String> canjearBoleto(String codigoBoleto) {
        try {
            Boleto boleto = boletoRepository.findByCodigoBoleto(codigoBoleto)
                    .orElseThrow(() -> new EventoException("Boleto no encontrado"));

            if (boleto.isCanjeado()) {
                throw new EventoException("El boleto ya fue canjeado");
            }

            LocalDateTime ahora = LocalDateTime.now();
            if (ahora.isBefore(boleto.getEvento().getFechaInicio()) ||
                    ahora.isAfter(boleto.getEvento().getFechaFin())) {
                throw new EventoException("El evento no está activo");
            }

            boleto.setCanjeado(true);
            boleto.setFechaCanje(ahora);
            boletoRepository.save(boleto);
            return ResponseEntity.ok("Boleto canjeado correctamente");
        } catch (EventoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * Obtiene los detalles de un evento específico.
     *
     * @param eventoId el ID del evento
     * @return un objeto DetalleEventoDTO con los detalles del evento
     */
    public DetalleEventoDTO obtenerDetalleEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        List<Boleto> boletos = boletoRepository.findByEventoId(eventoId);
        DetalleEventoDTO detalleEventoDTO = new DetalleEventoDTO();
        detalleEventoDTO.setNombre(evento.getNombre());
        detalleEventoDTO.setFechaInicio(evento.getFechaInicio());
        detalleEventoDTO.setFechaFin(evento.getFechaFin());
        detalleEventoDTO.setBoletosDisponibles(evento.getTotalBoletos() - evento.getBoletosVendidos());
        detalleEventoDTO.setBoletosVendidos(evento.getBoletosVendidos());
        detalleEventoDTO.setBoletosCanjeados((int) boletos.stream().filter(Boleto::isCanjeado).count());

        return detalleEventoDTO;
    }

    /**
     * Valida los datos de un evento.
     *
     * @param evento el evento a validar
     * @throws EventoException si los datos del evento no son válidos
     */
    private void validarEvento(Evento evento) {
        if (evento.getFechaInicio().isBefore(LocalDateTime.now())) {
            throw new EventoException("La fecha de inicio no puede ser en el pasado");
        }
        if (evento.getFechaFin().isBefore(evento.getFechaInicio())) {
            throw new EventoException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        if (evento.getTotalBoletos() < 1 || evento.getTotalBoletos() > 300) {
            throw new EventoException("El total de boletos debe estar entre 1 y 300");
        }
    }

    /**
     * Valida los datos de actualización de un evento.
     *
     * @param eventoExistente el evento existente
     * @param eventoActualizado los nuevos datos del evento
     * @throws EventoException si los datos de actualización no son válidos
     */
    private void validarActualizacionEvento(Evento eventoExistente, Evento eventoActualizado) {
        validarEvento(eventoActualizado);

        long boletosVendidos = boletoRepository.findByEventoId(eventoExistente.getId()).size();
        if (eventoActualizado.getTotalBoletos() < boletosVendidos) {
            throw new EventoException(
                    "No se puede reducir el total de boletos por debajo del número de boletos vendidos");
        }
    }

    /**
     * Genera un código único para un boleto.
     *
     * @return un código de boleto único
     */
    private String generarCodigoBoleto() {
        return UUID.randomUUID().toString();
    }
}
