package com.misiontic.ciclo4.pryAgendamientoCitas.Dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//import java.util.Set;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegistrarUsuarioDto {
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombreUsuario;
    private String apellidoUsuario;
    @NotNull(message = "El documento es obligatorio")
    @Min(value = 1000000, message = "El documento debe ser de 7 caracteres")
    private Long documentoUsuario;
    @NotEmpty(message = "El userName no puede estar vacio")
    @Size(min = 7, message = "La userName debe tener minimo 7 caracteres")
    private String userName;
    @NotEmpty(message = "La contraseña no puede estar vacio")
    @Size(min = 7, message = "La contraseña debe tener minimo 7 caracteres")
    private String password;
}
