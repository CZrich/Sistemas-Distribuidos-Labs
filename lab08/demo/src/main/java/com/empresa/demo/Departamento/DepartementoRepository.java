package com.empresa.demo.Departamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementoRepository extends JpaRepository<Departamento,Long> {
}
