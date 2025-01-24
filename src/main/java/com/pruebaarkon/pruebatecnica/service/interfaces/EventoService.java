package com.pruebaarkon.pruebatecnica.service.interfaces;

import com.pruebaarkon.pruebatecnica.model.dto.DetalleEventoDTO;
import com.pruebaarkon.pruebatecnica.model.dto.InfoBoletoDTO;
import com.pruebaarkon.pruebatecnica.model.entity.Boleto;
import com.pruebaarkon.pruebatecnica.model.entity.Evento;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EventoService {

    Evento crearEvento(Evento evento);

    ResponseEntity<String> actualizarEvento(Long id, Evento evento);

    ResponseEntity<String> eliminarEvento(Long id);

    DetalleEventoDTO obtenerDetalleEvento(Long id);

    InfoBoletoDTO venderBoleto(Long eventoId);

    ResponseEntity<String> canjearBoleto(String codigoBoleto);

}
