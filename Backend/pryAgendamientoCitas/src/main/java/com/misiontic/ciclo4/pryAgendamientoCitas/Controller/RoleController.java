package com.misiontic.ciclo4.pryAgendamientoCitas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.pryAgendamientoCitas.Entity.Role;
import com.misiontic.ciclo4.pryAgendamientoCitas.Service.RoleService;

@RestController
@RequestMapping("/api/rol")
public class RoleController {
    
    @Autowired
    RoleService roleService;

    @PostMapping("/create")
    public Role save(@RequestBody Role role) {
        return roleService.save(role);
    }
}
