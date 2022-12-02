package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.CitaService;

@RestController
@RequestMapping("/api/cita")
public class CitaController {

    @Autowired
    CitaService citaService;

    @PostMapping("/crear_agenda/{año}/{mes}/{dia}")
	public ResponseEntity<?> createAgenda(@RequestBody Cita cita,@PathVariable int año,  @PathVariable int mes, @PathVariable int dia){
		cita.setFechaCita(LocalDate.of(año, mes, dia));
		cita.setHoraCita(LocalTime.of(8, 0));
		return ResponseEntity.status(HttpStatus.CREATED).body(citaService.createAgenda(cita));
	}

    // @GetMapping("/prueba")
    // public List<Cita> validarCita(@RequestBody Cita cita){
    //     return citaService.validarCita(cita);
    // }
}
