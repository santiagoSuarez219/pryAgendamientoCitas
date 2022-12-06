package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.CitaRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.UsuarioRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ERol;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.Message;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;

@Service
public class CitaService {
    
    @Autowired
    CitaRepository citaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<Message> createAgenda(Cita cita) {
        if(validarMedico(cita)){
            return new ResponseEntity<Message>(new  Message(404, "El medico no existe en el sistema"),HttpStatus.NOT_FOUND); 
        }

        if(validarCita(cita).size() == 0){
            citaRepository.save(cita);
        }
        List<Cita> Citas = new ArrayList<>();

		LocalDate auxFecha = cita.getFechaCita();
		LocalTime auxHora = cita.getHoraCita().plusHours(1);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				Citas.add(new Cita(cita.getEstadoCita(),auxFecha,auxHora,cita.getMedico()));
				auxHora = auxHora.plusHours(1);
			}
			auxFecha = auxFecha.plusDays(1);
			auxHora = cita.getHoraCita();
		}
		Citas.add(new Cita(cita.getEstadoCita(),auxFecha.plusDays(-1),Citas.get(Citas.size() - 1).getHoraCita().plusHours(1),cita.getMedico()));
        citaRepository.saveAll(Citas.stream().filter(Cita -> validarCita(Cita).size() == 0).collect(Collectors.toList())); 
        return new ResponseEntity<Message>(new  Message(200, "Agenda Creada de forma exitosa"),HttpStatus.OK);            
	}

    public List<Cita> validarCita(Cita cita){
        List<Cita> citasFiltradas = citaRepository.findByFechaCitaMedico(cita.getFechaCita(),cita.getMedico().getIdUsuario());
        if(citasFiltradas.size() != 0){
            citasFiltradas = citasFiltradas.stream().filter(citaFiltrada -> citaFiltrada.getHoraCita().equals(cita.getHoraCita())).collect(Collectors.toList());
        }
        return citasFiltradas;
    }

    public Boolean validarMedico(Cita cita){
        return usuarioRepository.findByIdUsuario(cita.getMedico().getIdUsuario()).get().getRoles().stream().filter(rol -> rol.getNombreRol().equals(ERol.MEDICO)).collect(Collectors.toList()).size() == 0;
    }

    public List<Cita> findByFechaCitaMedico(){
        return citaRepository.findByFechaCitaMedico(LocalDate.of(2022, 12, 01), "8634574d-051a-4e25-a311-3f477adce709");
    }

    public List<Cita> getCitasDisponibles(LocalDate fechaCita) {
		return citaRepository.findByCitasDisponibles(fechaCita);
	}

    public List<Cita> getCitas(LocalDate fechaCita){
        return citaRepository.findByFechaCita(fechaCita);
    }

    public Optional<Cita> findById(String id){
        return citaRepository.findById(id);
    }

    public Cita update(Cita cita){
        return citaRepository.save(cita);
    }
}
