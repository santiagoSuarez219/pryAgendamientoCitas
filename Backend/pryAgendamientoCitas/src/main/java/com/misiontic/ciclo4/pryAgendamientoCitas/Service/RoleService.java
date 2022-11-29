package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

// import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.RoleRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ERol;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    // public List<Role> findAll(){
    //     return (List<Role>) roleRepository.findAll();
    // }

    public Optional<Role> findByNombreRol(ERol nombre){
        return roleRepository.findByNombreRol(nombre);
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }
}
