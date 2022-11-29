package com.misiontic.ciclo4.pryAgendamientoCitas.Repository;

import org.springframework.stereotype.Repository;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,String> {
    
    @Transactional(readOnly = true)
    @Query(value = "Select * from usuarios where user_name=:user and password=:pwd", nativeQuery = true) 
    public Usuario login(@Param("user") String user, @Param("pwd") String pwd);
    
    @Transactional(readOnly = true)
    public Usuario findByDocumentoUsuario(Long documentoUsuario);
    public Optional<Usuario> findByIdUsuario(String idUsuario);

}
