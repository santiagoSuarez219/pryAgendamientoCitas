package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.CitaService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.UsuarioService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.Message;

@RestController
@RequestMapping("/api/cita")
public class CitaController {

    @Autowired
    CitaService citaService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/crear_agenda/{año}/{mes}/{dia}")
	public ResponseEntity<Message> createAgenda(@RequestBody Cita cita,@PathVariable int año,  @PathVariable int mes, @PathVariable int dia,@RequestHeader String user, @RequestHeader String key){
        if (!usuarioService.validarCredenciales(user, key)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (usuarioService.validarUsuarioAdmin(user, key) == false) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        cita.setFechaCita(LocalDate.of(año, mes, dia));
		cita.setHoraCita(LocalTime.of(8, 0));
        return citaService.createAgenda(cita);
	}

    @GetMapping("/prueba")
    public Boolean validarMedico(@RequestBody Cita cita){
        return citaService.validarMedico(cita);
    }
}
