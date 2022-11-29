package com.misiontic.ciclo4.pryAgendamientoCitas.Dto;

import java.util.Set;

import lombok.Data;

@Data
public class CrearUsuarioDto {
    private String idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private Long documentoUsuario;
    private String userName;
    private String password;
    private Set<String> roles;
}
