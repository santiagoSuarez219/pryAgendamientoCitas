package com.misiontic.ciclo4.pryAgendamientoCitas.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ERol;

@Repository
public interface RoleRepository extends CrudRepository<Role,String> {
    public Optional<Role> findByNombreRol(ERol nombre);
}
