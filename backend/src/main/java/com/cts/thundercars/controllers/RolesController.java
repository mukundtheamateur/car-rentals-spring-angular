package com.cts.thundercars.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.RolesServices;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

	private final RolesServices roleServices;

    @Autowired
    public RolesController(RolesServices roleServices) {
        this.roleServices = roleServices;
    }

    @GetMapping(value= {"", "/"})
    public ResponseEntity<List<Roles>> getAllRoles() throws NotFoundException {
        return new ResponseEntity<>(roleServices.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getRoleById(@PathVariable Integer id) throws NotFoundException {
        Roles role = roleServices.getRolesById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping(value= "/create")
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) throws AlreadyExistsException {
        return new ResponseEntity<>(roleServices.saveRole(role), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) throws NotFoundException {
        roleServices.deleteRole(id);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    }

	
}
