package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.CitaRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;

@Service
public class CitaService {
    
    @Autowired
    CitaRepository citaRepository;

    public List<Cita> createAgenda(Cita cita) {
		List<Cita> Citas = new ArrayList<>();

		citaRepository.save(cita);
        
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
		return (List<Cita>) citaRepository.saveAll(Citas);
	}
}
