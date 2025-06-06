package com.empresa.demo.proyecto;


import com.empresa.demo.exception.RequestException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class ProyectoService {

    @Autowired
    private  ProyectoRepository proyectoRepository;


    public Proyecto createProyecto(Proyecto proyecto){

        return  proyectoRepository.save(proyecto);
    }

   // @Transactional
    public ResponseEntity updateProyecto(Proyecto proyecto, Long id){

       var project= getProyecto(id);
       project.setNomProy(proyecto.getNomProy());
       project.setIniFechProy(proyecto.getIniFechProy());
       project.setTerFechProy(proyecto.getTerFechProy());
        proyectoRepository.save(project);

        return  ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }


    public  Proyecto getProyecto(Long id){
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(id);
        if(proyectoOpt.isEmpty()){
            throw  new RequestException("project no found", HttpStatus.BAD_REQUEST);
        }
        return  proyectoOpt.get();
    }


}
