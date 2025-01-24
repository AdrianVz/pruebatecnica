package com.pruebaarkon.pruebatecnica.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad (Evento) en el sistema.
 */
@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    /**
     * Identificador único de la entidad Evento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del evento.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Fecha y hora del inicio del evento.
     */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Fecha y hora de finalización del evento.
     */
    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    /**
     * Número total de boletos disponibles para el evento.
     */
    @Column(name = "total_boletos", nullable = false)
    private Integer totalBoletos;

    
    @Column(name = "boletos_vendidos", nullable = false)
    private Integer boletosVendidos;

    /**
     * Lista de boletos asociados al evento.
     */
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Boleto> boletos = new ArrayList<>();
}


