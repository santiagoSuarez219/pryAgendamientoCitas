package com.misiontic.ciclo4.pryAgendamientoCitas.Repository;

import org.springframework.stereotype.Repository;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Usuario;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,String> {
    
}
