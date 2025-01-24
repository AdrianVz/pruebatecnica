package com.pruebaarkon.pruebatecnica.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object para la información de un boleto.
 */
@Getter
@Setter
public class InfoBoletoDTO {

    private String mensaje;

    private String codigoBoleto;

    private String evento;

    /**
     * Constructor para crear una instancia de InfoBoletoDTO.
     *
     * @param mensaje      el mensaje asociado al boleto
     * @param codigoBoleto el código del boleto
     * @param evento       el evento asociado al boleto
     */
    public InfoBoletoDTO(String mensaje, String codigoBoleto, String evento) {
        this.mensaje = mensaje;
        this.codigoBoleto = codigoBoleto;
        this.evento = evento;
    }
}
