package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;
import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.UsuarioRepository;

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
}
