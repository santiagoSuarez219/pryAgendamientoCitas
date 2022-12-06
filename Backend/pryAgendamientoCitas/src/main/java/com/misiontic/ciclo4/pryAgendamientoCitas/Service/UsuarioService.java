package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;
import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.UsuarioRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Security.Hash;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario login(String user, String pwd){
        return usuarioRepository.login(user, pwd);
    }

    public Usuario findByDocumentoUsuario(Long documentoUsuario){
        return usuarioRepository.findByDocumentoUsuario(documentoUsuario);
    }

    public boolean validarUsuario(String user, String key, String tipoUsuario) {
        Usuario usuario = usuarioRepository.findByUserName(user);
        try {
            int cantidad = 0;
            for (Role role : usuario.getRoles()) {
                if (role.getNombreRol().toString().equals(tipoUsuario)) {
                    cantidad++;
                }
            }
            if (cantidad == 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean validarCredenciales(String user, String key) {
        Usuario usuario = usuarioRepository.findByUserName(user);
        if (usuario == null) {
            return false;
        } else {
            if (Hash.sha1(usuario.getPassword() + Hash.sha1(user)).equals(key)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Optional<Usuario> findByIdUsuario(String idUsuario){
        return usuarioRepository.findById(idUsuario);
    }

}
