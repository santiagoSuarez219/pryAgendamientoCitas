package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.CitasDisponiblesDto;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.CitaService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.UsuarioService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ConvertEntity;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.Message;

@RestController
@RequestMapping("/api/cita")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    CitaService citaService;

    @Autowired
    UsuarioService usuarioService;

    ConvertEntity convertEntity = new ConvertEntity();

    @PostMapping("/crear_agenda/{año}/{mes}/{dia}")
	public ResponseEntity<Message> createAgenda(@RequestBody Cita cita,@PathVariable int año,  @PathVariable int mes, @PathVariable int dia,@RequestHeader String user, @RequestHeader String key){
        if (!usuarioService.validarCredenciales(user, key)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (usuarioService.validarUsuario(user, key,"ADMIN") == false) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        cita.setFechaCita(LocalDate.of(año, mes, dia));
		cita.setHoraCita(LocalTime.of(8, 0));
        return citaService.createAgenda(cita);
	}

    //Consultar agenda disponible
    // @GetMapping("/citas_disponibles")
	// public List<CitasDisponiblesDto> readCitasDisponibles(@RequestBody Cita cita){
    //     List<Cita> citas = citaService.getCitasDisponibles(cita.getFechaCita());
    //     List<CitasDisponiblesDto> citasDisponibles = new ArrayList<>();
    //     citas.stream().forEach(citaDisponible -> {
    //         citasDisponibles.add(new CitasDisponiblesDto(citaDisponible.getIdCita(),citaDisponible.getFechaCita(), citaDisponible.getHoraCita(), citaDisponible.getMedico()));
    //     });
    //     return citasDisponibles;
	// }

    @GetMapping("/citas_disponibles/{fecha}")
	public ResponseEntity<?> readCitasDisponibles(@PathVariable String fecha,@RequestHeader String user, @RequestHeader String key){
        if (!usuarioService.validarCredenciales(user, key) || !usuarioService.validarUsuario(user, key, "PACIENTE")) {
            return new ResponseEntity<Message>(new Message(401, "Debe iniciar sesion como paciente"),HttpStatus.UNAUTHORIZED);
        }
        List<Cita> citas = citaService.getCitasDisponibles(LocalDate.parse(fecha));
        List<CitasDisponiblesDto> citasDisponibles = new ArrayList<>();
        citas.stream().forEach(citaDisponible -> {
            citasDisponibles.add(new CitasDisponiblesDto(citaDisponible.getIdCita(),citaDisponible.getFechaCita(), citaDisponible.getHoraCita(), citaDisponible.getMedico()));
        });
        return ResponseEntity.status(HttpStatus.OK).body(citasDisponibles);
	}

    @PutMapping("/estado_cita")
    public ResponseEntity<Message> toggleEstadoCita(@RequestBody Cita cita,@RequestHeader String user, @RequestHeader String key){
        if (!usuarioService.validarCredenciales(user, key) || !usuarioService.validarUsuario(user, key, "PACIENTE")) {
            return new ResponseEntity<Message>(new Message(401, "Debe iniciar sesion como paciente"),HttpStatus.UNAUTHORIZED);
        }
        Optional<Cita> citaAgendar = citaService.findById(cita.getIdCita());
        if (citaAgendar.isPresent()) {
            citaAgendar.get().setEstadoCita(!citaAgendar.get().getEstadoCita());
            citaAgendar.get().setPaciente(cita.getPaciente());
            citaService.update(citaAgendar.get());
            return new ResponseEntity<Message>(new  Message(200, "Cita actualizada con exito"),HttpStatus.OK);
        }
        return new ResponseEntity<Message>(new  Message(400, "La cita no existe"),HttpStatus.BAD_REQUEST);            
    }
    @GetMapping("/citas/{fecha}")
	public ResponseEntity<?> readCitas(@PathVariable String fecha,@RequestHeader String user, @RequestHeader String key){
        if (!usuarioService.validarCredenciales(user, key) || !usuarioService.validarUsuario(user, key, "ADMIN")) {
            return new ResponseEntity<Message>(new Message(401, "Debe iniciar sesion como administrador"),HttpStatus.UNAUTHORIZED);
        }
        List<Cita> citas = citaService.getCitas(LocalDate.parse(fecha));
        return ResponseEntity.status(HttpStatus.OK).body(citas);
	}

    @GetMapping("/citas_paciente/{id}")
    public List<Cita> findByPaciente(@PathVariable String id){
        return citaService.findByPaciente(usuarioService.findByIdUsuario(id).get());
    }

    @GetMapping("/agenda_medico/{id}")
    public List<Cita> findByMedico(@PathVariable String id){
        return citaService.findByMedico(usuarioService.findByIdUsuario(id).get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable String id){
        citaService.deleteById(id);
        return new ResponseEntity<Message>(new Message(200, "Registro eliminado"),HttpStatus.OK);
    }
    
    // @GetMapping("/prueba")
    // public Boolean validarUsuario(@RequestHeader String user, @RequestHeader String key){
    //     return !usuarioService.validarUsuario(user, key, "MEDICO");
    // }
}
