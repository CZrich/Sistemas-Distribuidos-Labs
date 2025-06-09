package com.empresa.demo.domain.proyecto;

import com.empresa.demo.domain.contrato.Contrato;
import com.empresa.demo.domain.departamento.Departamento;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Proyecto")
@Table(name= "proyecto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@EqualsAndHashCode(of = "id")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProy;

    private String nomProy;

     private LocalDate iniFechProy;
     private LocalDate terFechProy;
     @ManyToOne
     @JoinColumn(name = "departamento_id")
     private Departamento departamento;


     @OneToMany(mappedBy = "proyectos",fetch =  FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
     private List<Contrato> contratos;

}
