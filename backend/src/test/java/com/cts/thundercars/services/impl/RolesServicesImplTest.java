package com.cts.thundercars.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.RolesRepository;

public class RolesServicesImplTest {

    @InjectMocks
    private RolesServicesImpl rolesServices;

    @Mock
    private RolesRepository rolesRepository;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllRoles_Positive() throws NotFoundException {
        when(rolesRepository.findAll()).thenReturn(Arrays.asList(new Roles(), new Roles()));
        assertEquals(2, rolesServices.getAllRoles().size());
        verify(rolesRepository, times(1)).findAll();
    }

    @Test
    public void getAllRoles_Negative() throws NotFoundException {
        when(rolesRepository.findAll()).thenReturn(Arrays.asList());
        assertThrows(NotFoundException.class, () -> rolesServices.getAllRoles());
        verify(rolesRepository, times(1)).findAll();
    }

    @Test
    public void getRolesById_Positive() throws NotFoundException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesRepository.findById(1)).thenReturn(Optional.of(role));
        assertEquals(role, rolesServices.getRolesById(1));
        verify(rolesRepository, times(1)).findById(1);
    }

    @Test
    public void getRolesById_Negative() throws NotFoundException {
        when(rolesRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> rolesServices.getRolesById(1));
        verify(rolesRepository, times(1)).findById(1);
    }

    @Test
    public void saveRole_Positive() throws AlreadyExistsException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesRepository.existsById(role.getId())).thenReturn(false);
        when(rolesRepository.save(role)).thenReturn(role);
        assertEquals(role, rolesServices.saveRole(role));
        verify(rolesRepository, times(1)).existsById(role.getId());
        verify(rolesRepository, times(1)).save(role);
    }

    @Test
    public void saveRole_Negative() throws AlreadyExistsException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesRepository.existsById(role.getId())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> rolesServices.saveRole(role));
        verify(rolesRepository, times(1)).existsById(role.getId());
    }

    @Test
    public void deleteRole_Positive() throws NotFoundException {
        Roles role = new Roles();
        role.setId(1);
        when(rolesRepository.findById(role.getId())).thenReturn(Optional.of(role));
        assertEquals("Role Deleted successfully", rolesServices.deleteRole(role.getId()));
        verify(rolesRepository, times(1)).findById(role.getId());
        verify(rolesRepository, times(1)).delete(role);
    }

    @Test
    public void deleteRole_Negative() throws NotFoundException {
        when(rolesRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> rolesServices.deleteRole(1));
        verify(rolesRepository, times(1)).findById(1);
    }
}
