package com.misiontic.ciclo4.pryAgendamientoCitas.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;
import com.misiontic.ciclo4.pryAgendamientoCitas.Repository.UsuarioRepository;
import com.misiontic.ciclo4.pryAgendamientoCitas.Security.Hash;
import com.misiontic.ciclo4.pryAgendamientoCitas.Utility.Message;

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

    

    public Message update(Usuario usuario){
        if (!usuarioRepository.findByIdUsuario(usuario.getIdUsuario()).get().getIdUsuario().equals("")){
            usuario.setPassword(Hash.sha1(usuario.getPassword()));
            usuarioRepository.save(usuario);
            return new Message(200, "ok");
        } else {
            return new Message(404, "usuario no encontrado");
        }
    }
}
