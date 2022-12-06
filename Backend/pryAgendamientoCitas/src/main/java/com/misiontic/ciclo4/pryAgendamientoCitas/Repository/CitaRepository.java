package com.misiontic.ciclo4.pryAgendamientoCitas.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;


@Repository
public interface CitaRepository extends CrudRepository<Cita, String> {
    
    @Query(value = "Select * from citas where medico =:medico and fecha_cita =:fechaCita", nativeQuery = true)
    public List<Cita> findByFechaCitaMedico(@Param("fechaCita") LocalDate fechaCita, @Param("medico") String medico);

    //Consultar Agenda Paciente
    @Query(value = "Select * from citas where paciente is null and fecha_cita =:fechaCita and estado_cita = false", nativeQuery = true)
	List<Cita> findByCitasDisponibles(@Param("fechaCita") LocalDate fechaCita);

    List<Cita> findByFechaCita(LocalDate fechaCita);

}
