package com.empresa.demo.domain.contrato;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.empresa.demo.domain.ingeniero.Ingeniero;

@Repository
public interface ContratoRepository  extends JpaRepository<Contrato, Long> {
    /*// Aquí puedes agregar métodos personalizados si es necesario
     @Query(""" 
            SELECT t FROM Topic t
            WHERE LOWER(t.title)
            LIKE LOWER(:title)
            AND LOWER(t.message) LIKE LOWER(:message)
            """)

            */
    //Optional<Topic> findByTitleAndMessage(@Param("title") String title, @Param("message") String message);

    @Query("SELECT c.ingenieros FROM Contrato c WHERE c.proyectos.idProy = :idProy")
    // Método para obtener la lista de ingenieros en un proyecto específico
    // Este método devuelve una lista de ingenieros asociados a un proyecto dado su ID
    // El parámetro idProy es el ID del proyecto para el cual se desean obtener los ingenieros
    Optional<List<Ingeniero>> getListIngenieroInProyecto( @Param("idProy")Long idProy);

}
