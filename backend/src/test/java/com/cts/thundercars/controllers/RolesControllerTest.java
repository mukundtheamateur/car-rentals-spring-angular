package com.cts.thundercars.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.RolesServices;

public class RolesControllerTest {

    @InjectMocks
    private RolesController rolesController;

    @Mock
    private RolesServices rolesServices;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllRoles_Positive() throws NotFoundException {
        when(rolesServices.getAllRoles()).thenReturn(Arrays.asList(new Roles(), new Roles()));
        ResponseEntity<List<Roles>> response = rolesController.getAllRoles();
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rolesServices, times(1)).getAllRoles();
    }

    @Test
    public void getRoleById_Positive() throws NotFoundException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesServices.getRolesById(1)).thenReturn(role);
        ResponseEntity<Roles> response = rolesController.getRoleById(1);
        assertEquals(role, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rolesServices, times(1)).getRolesById(1);
    }

    @Test
    public void getRoleById_Negative() throws NotFoundException {
        when(rolesServices.getRolesById(1)).thenThrow(new NotFoundException("Role not found"));
        assertThrows(NotFoundException.class, () -> rolesController.getRoleById(1));
        verify(rolesServices, times(1)).getRolesById(1);
    }
    @Test
    public void createRole_Positive() throws AlreadyExistsException, NotFoundException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesServices.saveRole(role)).thenReturn(role);
        ResponseEntity<Roles> response = rolesController.createRole(role);
        assertEquals(role, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rolesServices, times(1)).saveRole(role);
    }

    @Test
    public void createRole_Negative() throws AlreadyExistsException, NotFoundException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesServices.saveRole(role)).thenThrow(new AlreadyExistsException("Role already exists"));
        assertThrows(AlreadyExistsException.class, () -> rolesController.createRole(role));
        verify(rolesServices, times(1)).saveRole(role);
    }

    @Test
    public void deleteRole_Positive() throws NotFoundException {
        when(rolesServices.deleteRole(1)).thenReturn("Role deleted successfully");
        ResponseEntity<String> response = rolesController.deleteRole(1);
        assertEquals("Role deleted successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rolesServices, times(1)).deleteRole(1);
    }



    @Test
    public void deleteRole_Negative() throws NotFoundException {
        doThrow(new NotFoundException("Role not found")).when(rolesServices).deleteRole(1);
        assertThrows(NotFoundException.class, () -> rolesController.deleteRole(1));
        verify(rolesServices, times(1)).deleteRole(1);
    }

}

