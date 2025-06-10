package com.empresa.demo.domain.proyecto;


import com.empresa.demo.domain.contrato.ContratoRepository;
import com.empresa.demo.domain.departamento.Departamento;
import com.empresa.demo.domain.departamento.DepartementoRepository;
import com.empresa.demo.domain.ingeniero.Ingeniero;
import com.empresa.demo.exception.RequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProyectoService {

    @Autowired
    private  ProyectoRepository proyectoRepository;
    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private DepartementoRepository departamentoRepository;

  public Proyecto createProyecto(ProyectoDTO proyectotDto) {
     Departamento departamento = departamentoRepository.findById(proyectotDto.getIdDep())
        .orElseThrow(() -> new RequestException("Departamento no encontrado", HttpStatus.BAD_REQUEST));

    Proyecto proyecto = new Proyecto();
    proyecto.setNomProy(proyectotDto.getNomProy());
    proyecto.setIniFechProy(proyectotDto.getIniFechProy());
    proyecto.setTerFechProy(proyectotDto.getTerFechProy());
    proyecto.setDepartamento(departamento);

    Proyecto saved = proyectoRepository.save(proyecto);
    return  saved;
}

    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }
    
    public List<Ingeniero> getIngenierosByProyecto(Long id) {
      Optional<List<Ingeniero>> ingenierosOpt = contratoRepository.getListIngenieroInProyecto(id);
        if (ingenierosOpt.isPresent()) {
            return ingenierosOpt.get();
        } else {
            throw new RequestException("Ingenieros not found for the given project ID", HttpStatus.NOT_FOUND);
        }
      
    }
    public ResponseEntity<Void> updateProyecto(Proyecto proyecto, Long id){

       var project= getProyecto(id);
       project.setNomProy(proyecto.getNomProy());
       project.setIniFechProy(proyecto.getIniFechProy());
       project.setTerFechProy(proyecto.getTerFechProy());
       project.setDepartamento(proyecto.getDepartamento());
       // proyectoRepository.save(project);

        return  ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }


    public  Proyecto getProyecto(Long id){
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(id);
        if(proyectoOpt.isEmpty()){
            throw  new RequestException("project no found", HttpStatus.BAD_REQUEST);
        }
        return  proyectoOpt.get();
    }


    public ResponseEntity<Void> deleteProyecto(Long id) {
        var proyecto = getProyecto(id);
        proyectoRepository.delete(proyecto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
