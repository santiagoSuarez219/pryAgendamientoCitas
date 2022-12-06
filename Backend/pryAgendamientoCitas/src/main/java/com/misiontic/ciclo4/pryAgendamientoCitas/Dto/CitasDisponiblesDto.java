package com.misiontic.ciclo4.pryAgendamientoCitas.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CitasDisponiblesDto {
    private String idCita;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private Usuario medico;
}
