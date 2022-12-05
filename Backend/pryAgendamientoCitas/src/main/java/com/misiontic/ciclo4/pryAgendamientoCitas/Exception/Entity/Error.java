package com.misiontic.ciclo4.pryAgendamientoCitas.Exception.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String field;
    private String message;
}