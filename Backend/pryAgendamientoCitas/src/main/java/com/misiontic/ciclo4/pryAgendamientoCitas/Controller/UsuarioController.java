package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.CrearUsuarioDto;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.RoleService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.UsuarioService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ConvertEntity;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ERol;

@RestController
@RequestMapping("/api/usuario/")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    RoleService roleService;

    ConvertEntity convertEntity = new ConvertEntity();

    @PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CrearUsuarioDto usuario){
		Set<String> strRoles = usuario.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role rol = roleService.findByNombreRol(ERol.USUARIO).get();
            roles.add(rol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "Admin":
                        Role rolAdmin = roleService.findByNombreRol(ERol.ADMIN).get();
                        roles.add(rolAdmin);
                        break;
                    case "Usuario":
                        Role rolUser = roleService.findByNombreRol(ERol.USUARIO).get();
                        roles.add(rolUser);
                        break;
                }
            });
        }
        Usuario usuarioSave = (Usuario) convertEntity.convert(usuario, new Usuario());
        usuarioSave.setRoles(roles);
        return new ResponseEntity<>(usuarioService.guardarUsuario(usuarioSave),HttpStatus.CREATED);
	}
}
