package com.empresa.demo.Departamento;

import com.empresa.demo.exception.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private  DepartementoRepository departementoRepository;


    public  Departamento creatDepartamento(Departamento departamento){

        return  departementoRepository.save(departamento);

    }


    public  Departamento getDepartamento(Long id){

        Optional<Departamento> departamentoOpt=departementoRepository.findById(id);

        if(departamentoOpt.isEmpty()){
            throw  new RequestException("Departamento no found", HttpStatus.BAD_REQUEST);
        }
        return  departamentoOpt.get();
    }

    public ResponseEntity<Void> updateDepartamento(Long id,Departamento departamento){
         var depart=getDepartamento(id);

         depart.setNomDep(departamento.getNomDep());
         depart.setTelDep(departamento.getTelDep());
         depart.setFaxDep(departamento.getFaxDep());
         //depart.set

         return  ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    public  ResponseEntity<Void> deleteDeparatamento( Long id){
         if(!departementoRepository.existsById(id)){
             throw  new RequestException(" no  found with "+ id +" id",HttpStatus.BAD_REQUEST);
         }
          departementoRepository.deleteById(id);
         return  ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }
}
