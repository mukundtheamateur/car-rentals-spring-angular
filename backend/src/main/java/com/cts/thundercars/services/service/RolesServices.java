package com.cts.thundercars.services.service;

import java.util.List;

import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;


public interface RolesServices {
    List<Roles> getAllRoles() throws NotFoundException;
    Roles saveRole(Roles role) throws AlreadyExistsException;
    Roles getRolesById(Integer id) throws NotFoundException;
    String deleteRole(Integer id) throws NotFoundException;
}

