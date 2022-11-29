package com.misiontic.ciclo4.pryAgendamientoCitas.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ERol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idRole;

    @Enumerated(EnumType.STRING)
    private ERol nombreRol;

    public Role(ERol nombreRol) {
        this.nombreRol = nombreRol;
    }
}
