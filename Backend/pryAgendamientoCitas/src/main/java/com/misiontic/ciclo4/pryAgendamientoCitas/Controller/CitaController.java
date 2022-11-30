package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Service.CitaService;

@RestController
@RequestMapping("/api/cita")
public class CitaController {

    @Autowired
    CitaService citaService;
}
