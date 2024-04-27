package com.cts.thundercars.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.entity.User;
import com.cts.thundercars.exceptions.NoUserFoundException;
import com.cts.thundercars.services.service.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServices userServices;

    private MockMvc mockMvc;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getAllUsers_Positive() throws Exception {
        when(userServices.getUsers()).thenReturn(Arrays.asList(new User(), new User()));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
        verify(userServices, times(1)).getUsers();
    }

    @Test
    public void getUserById_Positive() throws Exception {
        User user = new User();
        user.setId(1);
        when(userServices.getUserById(1)).thenReturn(user);
        mockMvc.perform(get("/api/users/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
        verify(userServices, times(1)).getUserById(1);
    }

    @Test
    public void getUserById_Negative() throws Exception {
        when(userServices.getUserById(1)).thenReturn(null);
        mockMvc.perform(get("/api/users/id/1"))
                .andExpect(status().isNotFound());
        verify(userServices, times(1)).getUserById(1);
    }


    @Test
    public void createUser_Positive() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        when(userServices.saveUser(user)).thenReturn(user);
        mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@test.com"));
        verify(userServices, times(1)).saveUser(user);
    }

    @Test
    public void deleteUser_Positive() throws Exception {
        when(userServices.deleteUser(1)).thenReturn(new ResponseEntity<>("User deleted successfully", HttpStatus.OK));
        mockMvc.perform(delete("/api/users/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
        verify(userServices, times(1)).deleteUser(1);
    }

    @Test
    public void deleteUser_Negative() throws Exception {
        when(userServices.deleteUser(1)).thenReturn(new ResponseEntity<>("User does not exist, so it can't be deleted", HttpStatus.NOT_FOUND));
        mockMvc.perform(delete("/api/users/id/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User does not exist, so it can't be deleted"));
        verify(userServices, times(1)).deleteUser(1);
    }



    @Test
    public void updateUser_Positive() throws Exception {
        User userUpdates = new User();
        userUpdates.setEmail("test@test.com");
        when(userServices.updateUser(1, userUpdates)).thenReturn(userUpdates);
        mockMvc.perform(put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userUpdates)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@test.com"));
        verify(userServices, times(1)).updateUser(1, userUpdates);
    }

    @Test
    public void updateUser_Negative() throws Exception {
        User userUpdates = new User();
        userUpdates.setEmail("test@test.com");
        when(userServices.updateUser(1, userUpdates)).thenReturn(null);
        mockMvc.perform(put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userUpdates)))
                .andExpect(status().isNotFound());
        verify(userServices, times(1)).updateUser(1, userUpdates);
    }



    @Test
    public void getUsersByRoleId_Positive() throws Exception {
        when(userServices.getUsersByRoleId(1)).thenReturn(Arrays.asList(new User(), new User()));
        mockMvc.perform(get("/api/users/role/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
        verify(userServices, times(1)).getUsersByRoleId(1);
    }

    @Test
    public void getUsersByRoleId_Negative() throws Exception {
        when(userServices.getUsersByRoleId(1)).thenThrow(new NoUserFoundException("Users found = None"));
        mockMvc.perform(get("/api/users/role/1"))
                .andExpect(status().isNotFound());
        verify(userServices, times(1)).getUsersByRoleId(1);
    }





    @Test
    public void getUserBookings_Positive() throws Exception {
        when(userServices.getBookingsByUserId(1)).thenReturn(Arrays.asList(new Bookings(), new Bookings()));
        mockMvc.perform(get("/api/users/id/1/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
        verify(userServices, times(1)).getBookingsByUserId(1);
    }

    @Test
    public void getUserBookings_Negative() throws Exception {
        when(userServices.getBookingsByUserId(1)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/users/id/1/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        verify(userServices, times(1)).getBookingsByUserId(1);
    }


}
