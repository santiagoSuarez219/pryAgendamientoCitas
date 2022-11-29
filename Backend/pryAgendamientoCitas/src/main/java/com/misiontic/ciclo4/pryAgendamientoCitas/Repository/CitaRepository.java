package com.misiontic.ciclo4.pryAgendamientoCitas.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Cita;

@Repository
public interface CitaRepository extends CrudRepository<Cita, String> {
    
}
