package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.CrearUsuarioDto;
import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.CredencialesDto;
import com.misiontic.ciclo4.pryAgendamientoCitas.Dto.RegistrarUsuarioDto;
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
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    RoleService roleService;

    ConvertEntity convertEntity = new ConvertEntity();

    //El administrador es el unico que puede crear usuarios en el sistema
    @PostMapping("/create")
	public ResponseEntity<Message> create(@Valid @RequestBody CrearUsuarioDto usuario, @RequestHeader String user, @RequestHeader String key){
        if (!usuarioService.validarCredenciales(user, key) || !usuarioService.validarUsuario(user, key, "ADMIN")) {
            return new ResponseEntity<Message>(new  Message(401, "Debe iniciar sesion como administrador"),HttpStatus.UNAUTHORIZED);
        }
        if (usuarioService.findByDocumentoUsuario(usuario.getDocumentoUsuario()) != null){
            return new ResponseEntity<Message>(new  Message(406, "El usuario ya existe"),HttpStatus.NOT_ACCEPTABLE);            
        }

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
        usuarioService.guardarUsuario(usuarioSave);
        return new ResponseEntity<Message>(new  Message(201, "El usuario con numero de identificacion " + usuario.getDocumentoUsuario() + " fue habilitado con exito"),HttpStatus.CREATED);            
	}
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestHeader String user, @RequestHeader String pwd){
        Usuario usuario = usuarioService.login(user, Hash.sha1(pwd));
        if (usuario != null){
            System.out.println(usuario.getRoles());
            return new ResponseEntity<>(new CredencialesDto(usuario.getIdUsuario(),usuario.getUserName(),Hash.sha1(Hash.sha1(pwd) + Hash.sha1(usuario.getUserName())),usuario.getRoles()),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(401, "Error de credenciales"), HttpStatus.UNAUTHORIZED);
        }
    }
    //Registrar Usuario
    @PutMapping("/registro_usuario")
    public ResponseEntity<Message> RegistrarUsuario(@Valid @RequestBody RegistrarUsuarioDto usuario){
        Usuario usuarioAux = usuarioService.findByDocumentoUsuario(usuario.getDocumentoUsuario());
        if (usuarioAux == null){
            return new ResponseEntity<Message>(new  Message(401, "No puedes registrarte, no estas habilitado"),HttpStatus.UNAUTHORIZED);            
        }
        usuarioAux.setPassword(Hash.sha1(usuario.getPassword()));
        usuarioAux.setNombreUsuario(usuario.getNombreUsuario());
        usuarioAux.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioAux.setUserName(usuario.getUserName());
        usuarioService.guardarUsuario(usuarioAux);
        return new ResponseEntity<Message>(new  Message(200, "Usuario registrado con exito"),HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/listar_pacientes")
    public List<Usuario> findByRolePaciente(){
        return usuarioService.findByRol(roleService.findByNombreRol(ERol.PACIENTE).get());
    }

    @GetMapping("/listar_medicos")
    public List<Usuario> findByRoleMedico(){
        return usuarioService.findByRol(roleService.findByNombreRol(ERol.MEDICO).get());
    }

    @GetMapping("buscar_usuario/{id}")
    public Optional<Usuario> findById(@PathVariable String id) {
        return usuarioService.findByIdUsuario(id);
    }

    @PutMapping("/editar_usuario")
    public ResponseEntity<Message> EditarUsuario(@Valid @RequestBody Usuario usuario){
        Usuario usuarioAux = usuarioService.findByIdUsuario(usuario.getIdUsuario()).get();
        usuarioAux.setPassword(Hash.sha1(usuario.getPassword()));
        usuarioAux.setNombreUsuario(usuario.getNombreUsuario());
        usuarioAux.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioAux.setDocumentoUsuario(usuario.getDocumentoUsuario());
        usuarioAux.setUserName(usuario.getUserName());
        usuarioService.guardarUsuario(usuarioAux);
        return new ResponseEntity<Message>(new  Message(200, "Usuario actualizado con exito"),HttpStatus.OK);
    }
}
