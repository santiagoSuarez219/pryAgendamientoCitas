package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.CitaRepository;

@Service
public class CitaService {
    
    @Autowired
    CitaRepository citaRepository;
}
