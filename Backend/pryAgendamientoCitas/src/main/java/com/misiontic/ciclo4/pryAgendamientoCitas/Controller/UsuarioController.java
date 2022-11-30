package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.CrearUsuarioDto;
import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.CredencialesDto;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;
import com.misiontic.ciclo4.pryAgendamientoCitas.Security.Hash;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.RoleService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.UsuarioService;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ConvertEntity;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.ERol;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.Message;

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
                    case "Paciente":
                        Role rolPaciente = roleService.findByNombreRol(ERol.PACIENTE).get();
                        roles.add(rolPaciente);
                        break;
                    case "Medico":
                        Role rolMedico = roleService.findByNombreRol(ERol.MEDICO).get();
                        roles.add(rolMedico);
                        break;
                }
            });
        }
        Usuario usuarioSave = (Usuario) convertEntity.convert(usuario, new Usuario());
        usuarioSave.setRoles(roles);
        return new ResponseEntity<>(usuarioService.guardarUsuario(usuarioSave),HttpStatus.CREATED);
	}

    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestHeader String user, @RequestHeader String pwd){
        Usuario usuario = usuarioService.login(user, Hash.sha1(pwd));
        if (usuario != null){
            return new ResponseEntity<>(new CredencialesDto(usuario.getUserName(),
            Hash.sha1(Hash.sha1(pwd) + Hash.sha1(usuario.getUserName()))),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(401, "Error de credenciales"), HttpStatus.UNAUTHORIZED);
        }
    }
    //Registrar Usuario
    @PutMapping("/registro_usuario")
    public ResponseEntity<Message> RegistrarUsuario(@RequestBody Usuario usuario){
        if (usuarioService.findByDocumentoUsuario(usuario.getDocumentoUsuario()) == null){
            return new ResponseEntity<Message>(new  Message(401, "No puedes registrarte"),HttpStatus.UNAUTHORIZED);            
        }
        Message message = usuarioService.update(usuario);
        return new ResponseEntity<Message>(new Message(message.getStatus(), message.getMessage()),HttpStatus.valueOf(message.getStatus()));
    }
}
