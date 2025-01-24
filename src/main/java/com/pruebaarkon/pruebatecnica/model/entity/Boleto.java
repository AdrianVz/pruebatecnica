package com.pruebaarkon.pruebatecnica.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Representa la entidad (Boleto) en el sistema.
 */

@Entity
@Table(name = "boletos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boleto {

    /**
     * Identificador único en la entidad Boleto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relación muchos-a-uno con la entidad.
     */
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    /**
     * Código único del boleto.
     */
    @Column(name = "codigo_boleto", nullable = false, unique = true)
    private String codigoBoleto;

    /**
     * Indica si el boleto ha sido canjeado.
     */
    @Column(name = "canjeado")
    private boolean canjeado = false;

    /**
     * Fecha y hora de la venta del boleto.
     */
    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    /**
     * Fecha y hora en la que el boleto fue canjeado.
     */
    @Column(name = "fecha_canje")
    private LocalDateTime fechaCanje;

}
