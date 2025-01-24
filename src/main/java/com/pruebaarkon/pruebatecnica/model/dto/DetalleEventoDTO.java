package com.pruebaarkon.pruebatecnica.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Clase DTO para obtener los detalles del evento.
 */
@Getter
@Setter
public class DetalleEventoDTO {

    /**
     * Nombre del evento.
     */
    private String nombre;

    /**
     * Fecha de inicio del evento.
     */
    private LocalDateTime fechaInicio;

    /**
     * Fecha fin del evento.
     */
    private LocalDateTime fechaFin;

    /**
     * Total de boletos disponibles.
     */
    private int boletosDisponibles;

    /**
     * Total de boletos vendidos.
     */
    private int boletosVendidos;

    /**
     * Total de boletos canjeados.
     */
    private int boletosCanjeados;

}
