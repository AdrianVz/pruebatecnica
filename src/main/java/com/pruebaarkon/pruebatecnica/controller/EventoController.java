package com.pruebaarkon.pruebatecnica.controller;

import com.pruebaarkon.pruebatecnica.exception.EventoException;
import com.pruebaarkon.pruebatecnica.model.dto.DetalleEventoDTO;
import com.pruebaarkon.pruebatecnica.model.dto.InfoBoletoDTO;
import com.pruebaarkon.pruebatecnica.model.entity.Boleto;
import com.pruebaarkon.pruebatecnica.model.entity.Evento;
import com.pruebaarkon.pruebatecnica.service.interfaces.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> crearEvento(@Validated @RequestBody Evento evento) {
        return ResponseEntity.ok(eventoService.crearEvento(evento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEvento(@PathVariable Long id, @Validated @RequestBody Evento evento) {
        return eventoService.actualizarEvento(id, evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEvento(@PathVariable Long id) {
        return eventoService.eliminarEvento(id);
    }

    @PostMapping("/{id}/boletos")
    public InfoBoletoDTO venderBoleto(@PathVariable Long id) {
        return eventoService.venderBoleto(id);
    }

    @PostMapping("/boletos/{codigoBoleto}/canjear")
    public ResponseEntity<String> canjearBoleto(@PathVariable String codigoBoleto) {
        return eventoService.canjearBoleto(codigoBoleto);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<DetalleEventoDTO> obtenerDetalleEvento(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerDetalleEvento(id));

    }

}
