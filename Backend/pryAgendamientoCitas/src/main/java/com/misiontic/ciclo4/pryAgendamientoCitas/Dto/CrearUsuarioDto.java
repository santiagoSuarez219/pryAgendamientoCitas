package com.misiontic.ciclo4.pryAgendamientoCitas.Dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class CrearUsuarioDto {
    private String idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    @NotNull(message = "El documento es obligatorio")
    private Long documentoUsuario;
    private String userName;
    private String password;
    @NotEmpty(message = "El rol es obligatorio")
    private Set<String> roles;
}
