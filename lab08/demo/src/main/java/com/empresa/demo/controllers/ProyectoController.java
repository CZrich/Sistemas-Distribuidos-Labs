package com.empresa.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.demo.domain.proyecto.Proyecto;
import com.empresa.demo.domain.proyecto.ProyectoService;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {


    private ProyectoService proyectoService;

    // Constructor injection for ProyectoService    
    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }



    // Add methods for handling requests related to Proyectos here
    @PostMapping
    public  Proyecto createProyecto(@RequestBody  Proyecto proyecto) {
        return proyectoService.createProyecto(proyecto);
    }

    @GetMapping("/{id}")
    public Proyecto getProyecto(@PathVariable Long id) {
        return proyectoService.getProyecto(id);
    }

    @PostMapping("/{id}")
     public ResponseEntity<Void> updateProyecto(@RequestBody Proyecto proyecto,@PathVariable Long id) {
        return proyectoService.updateProyecto(proyecto, id);
     }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        return proyectoService.deleteProyecto(id);
    }

}
