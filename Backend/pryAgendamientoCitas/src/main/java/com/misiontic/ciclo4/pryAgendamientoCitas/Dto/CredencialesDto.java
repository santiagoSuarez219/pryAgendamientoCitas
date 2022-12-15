package com.misiontic.ciclo4.pryAgendamientoCitas.Dto;

import java.util.Set;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredencialesDto {
    private String idUsuario;
    private String userName;
    private String key;
    private Set<Role> roles;
}
