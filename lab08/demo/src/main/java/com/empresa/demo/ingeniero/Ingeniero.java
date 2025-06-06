package com.empresa.demo.ingeniero;

import com.empresa.demo.contrato.Contrato;
import com.empresa.demo.proyecto.Proyecto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "Ingeniero")
@Table(name = "ingeniero")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Ingeniero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idIng;
    private  String namIng;
    private  String carIng;
    private  String espIng;
    @OneToMany(mappedBy = "ingenieros",fetch = FetchType.LAZY)
    private List<Contrato> contratos;

}
