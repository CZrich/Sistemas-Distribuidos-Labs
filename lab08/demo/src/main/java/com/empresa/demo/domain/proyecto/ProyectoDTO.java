package com.empresa.demo.domain.proyecto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProyectoDTO {
    private String nomProy;
    private LocalDate iniFechProy;
    private LocalDate terFechProy;
    private Long idDep; // solo el ID del departamento

    // Getters y Setters
}