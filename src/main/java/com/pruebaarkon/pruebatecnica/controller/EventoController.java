package com.pruebaarkon.pruebatecnica.controller;

import com.pruebaarkon.pruebatecnica.model.dto.DetalleEventoDTO;
import com.pruebaarkon.pruebatecnica.model.dto.InfoBoletoDTO;
import com.pruebaarkon.pruebatecnica.model.entity.Evento;
import com.pruebaarkon.pruebatecnica.service.interfaces.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestionar los eventos.
 */
@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    /**
     * Crea un nuevo evento.
     *
     * @param evento el evento a crear
     * @return el evento creado
     */
    @PostMapping
    public ResponseEntity<Evento> crearEvento(@Validated @RequestBody Evento evento) {
        return ResponseEntity.ok(eventoService.crearEvento(evento));
    }

    /**
     * Actualiza un evento existente.
     *
     * @param id     el ID del evento a actualizar
     * @param evento los nuevos datos del evento
     * @return una respuesta indicando el resultado de la operación
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEvento(@PathVariable Long id, @Validated @RequestBody Evento evento) {
        return eventoService.actualizarEvento(id, evento);
    }

    /**
     * Elimina un evento.
     *
     * @param id el ID del evento a eliminar
     * @return una respuesta indicando el resultado de la operación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEvento(@PathVariable Long id) {
        return eventoService.eliminarEvento(id);
    }

    /**
     * Vende un boleto para un evento.
     *
     * @param id el ID del evento
     * @return la información del boleto vendido
     */
    @PostMapping("/{id}/boletos")
    public InfoBoletoDTO venderBoleto(@PathVariable Long id) {
        return eventoService.venderBoleto(id);
    }

    /**
     * Canjea un boleto.
     *
     * @param codigoBoleto el código del boleto a canjear
     * @return una respuesta indicando el resultado de la operación
     */
    @PostMapping("/boletos/{codigoBoleto}/canjear")
    public ResponseEntity<String> canjearBoleto(@PathVariable String codigoBoleto) {
        return eventoService.canjearBoleto(codigoBoleto);
    }

    /**
     * Obtiene el detalle de un evento.
     *
     * @param id el ID del evento
     * @return el detalle del evento
     */
    @GetMapping("/{id}/detalle")
    public ResponseEntity<DetalleEventoDTO> obtenerDetalleEvento(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerDetalleEvento(id));
    }

}
