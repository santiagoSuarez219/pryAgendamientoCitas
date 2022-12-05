package com.misiontic.ciclo4.pryAgendamientoCitas.Exception.Entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String message;
    private Date timeStamp;
    List<Error> errors;
}

