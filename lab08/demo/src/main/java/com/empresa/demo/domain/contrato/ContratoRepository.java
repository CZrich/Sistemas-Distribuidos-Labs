package com.empresa.demo.domain.contrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository  extends JpaRepository<Contrato, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
