package com.empresa.demo.Departamento;

import com.empresa.demo.proyecto.Proyecto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "Departamento")
@Table(name = "departamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDep;
    private  String nomDep;
    private String  telDep;
    private  String faxDep;
    @OneToMany(mappedBy = "departamento",fetch = FetchType.LAZY)

    private List<Proyecto> proyectos;
}
