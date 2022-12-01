package com.misiontic.ciclo4.pryAgendamientoCitas.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idCita;
    private Boolean estadoCita;
    private LocalDate fechaCita;
    private LocalTime horaCita;

    @ManyToOne
    @JoinColumn(name = "paciente")
    private Usuario paciente;

    @ManyToOne
    @JoinColumn(name = "medico")
    private Usuario medico;

    public Cita(Boolean estadoCita, LocalDate fechaCita, LocalTime horaCita, Usuario medico) {
        this.estadoCita = estadoCita;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.medico = medico;
    }

}
