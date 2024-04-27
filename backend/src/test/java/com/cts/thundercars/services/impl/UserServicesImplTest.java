package com.cts.thundercars.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.entity.User;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NoUserFoundException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.exceptions.RoleIdNotFoundException;
import com.cts.thundercars.repository.BookingsRepository;
import com.cts.thundercars.repository.UserRepository;

class UserServicesImplTest {

	@InjectMocks
    private UserServicesImpl userServices;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private BookingsRepository bookingsRepository;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers_Positive() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        assertEquals(2, userServices.getUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserById_Positive() throws NotFoundException {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertEquals(user, userServices.getUserById(1));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void getUserById_Negative() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServices.getUserById(1));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void saveUser_Positive() throws AlreadyExistsException, NotFoundException {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userServices.saveUser(user));
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void saveUser_Negative() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> userServices.saveUser(user));
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    public void deleteUser_Positive() throws NotFoundException {
        User user = new User();
        user.setId(1);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(new ResponseEntity<String>("Deleted successfully", HttpStatus.OK), userServices.deleteUser(user.getId()));
        verify(userRepository, times(1)).existsById(user.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUser_Negative() {
        when(userRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> userServices.deleteUser(1));
        verify(userRepository, times(1)).existsById(1);
    }

    @Test
    public void getUserByEmail_Positive() throws NotFoundException {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertEquals(user, userServices.getUserByEmail(user.getEmail()));
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void getUserByEmail_Negative() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServices.getUserByEmail("test@test.com"));
        verify(userRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void getUsersByRoleId_Positive() throws NoUserFoundException {
        when(userRepository.findByRoleId(1)).thenReturn(Arrays.asList(new User(), new User()));
        assertEquals(2, userServices.getUsersByRoleId(1).size());
        verify(userRepository, times(1)).findByRoleId(1);
    }

    @Test
    public void getUsersByRoleId_Negative() {
        when(userRepository.findByRoleId(1)).thenReturn(Collections.emptyList());
        assertThrows(NoUserFoundException.class, () -> userServices.getUsersByRoleId(1));
        verify(userRepository, times(1)).findByRoleId(1);
    }

    @Test
    public void updateUser_Positive() throws NotFoundException, RoleIdNotFoundException {
        User user = new User();
        user.setId(1);
        User userUpdates = new User();
        userUpdates.setEmail("test@test.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userServices.updateUser(user.getId(), userUpdates));
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateUser_Negative() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServices.updateUser(1, new User()));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void getBookingsByUserId_Positive() throws NotFoundException {
        when(bookingsRepository.findByUserId(1)).thenReturn(Arrays.asList(new Bookings(), new Bookings()));
        assertEquals(2, userServices.getBookingsByUserId(1).size());
        verify(bookingsRepository, times(1)).findByUserId(1);
    }

    @Test
    public void getBookingsByUserId_Negative() {
        when(bookingsRepository.findByUserId(1)).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> userServices.getBookingsByUserId(1));
        verify(bookingsRepository, times(1)).findByUserId(1);
    }


}
