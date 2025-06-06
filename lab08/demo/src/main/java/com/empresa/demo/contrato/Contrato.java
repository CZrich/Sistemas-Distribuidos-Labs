package com.empresa.demo.contrato;

import com.empresa.demo.ingeniero.Ingeniero;
import com.empresa.demo.proyecto.Proyecto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.List;

@Entity(name = "Contrato")
@Table(name = "contrato")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCon;

    @ManyToOne
    private List<Ingeniero> ingenieros;

    @ManyToOne
    private List<Proyecto> proyectos;

    private  boolean status;

}
