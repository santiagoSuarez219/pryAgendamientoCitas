package com.misiontic.ciclo4.pryAgendamientoCitas.Utility;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;
import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.RoleRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Security.Hash;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.RoleService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.UsuarioService;

@Component
public class DatosIniciales implements CommandLineRunner{

    @Autowired
    RoleService roleService;

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findAll().size() == 0) {
            Role rolAdmin = new Role(ERol.ADMIN);
            Role rolUser = new Role(ERol.USUARIO);
            Role rolMedico = new Role(ERol.MEDICO);
            Role rolPaciente = new Role(ERol.PACIENTE);
            roleService.save(rolAdmin);
            roleService.save(rolUser);
            roleService.save(rolMedico);
            roleService.save(rolPaciente);
        } 
        if (usuarioService.findAll().size() == 0) {
            Role userRole = roleRepository.findByNombreRol(ERol.ADMIN).get();
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("admin");
            usuario.setApellidoUsuario("admin");
            usuario.setDocumentoUsuario((long) 1111111);
            usuario.setUserName("admin");
            usuario.setPassword(Hash.sha1("123456"));
            usuario.setRoles(roles);
            usuarioService.guardarUsuario(usuario);
        }
    }
}
