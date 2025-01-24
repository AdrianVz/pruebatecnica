package com.pruebaarkon.pruebatecnica.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoBoletoDTO {

    private String mensaje;

    private String codigoBoleto;

    private String evento;

    public InfoBoletoDTO(String mensaje, String codigoBoleto, String evento) {
        this.mensaje = mensaje;
        this.codigoBoleto = codigoBoleto;
        this.evento = evento;
    }
}
