package com.cts.thundercars.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.CarDealerRepository;
import com.cts.thundercars.repository.RolesRepository;

public class CarDealerServicesImplTest {

    @InjectMocks
    private CarDealerServicesImpl carDealerService;

    @Mock
    private CarDealerRepository carDealerRepository;

    @Mock
    private RolesRepository rolesRepository;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllCarDealers_NotFound() {
        when(carDealerRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> carDealerService.getAllCarDealers());
        verify(carDealerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCarDealerById() throws NotFoundException {
        CarDealer carDealer = new CarDealer();
        carDealer.setId(1);
        when(carDealerRepository.existsById(1)).thenReturn(true);
        when(carDealerRepository.findById(1)).thenReturn(Optional.of(carDealer));
        CarDealer found = carDealerService.getCarDealerById(1);
        assertEquals(1, found.getId());
        verify(carDealerRepository, times(1)).existsById(1);
        verify(carDealerRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCarDealerById_NotFound() {
        when(carDealerRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> carDealerService.getCarDealerById(1));
        verify(carDealerRepository, times(1)).existsById(1);
    }

    @Test
    public void testSaveCarDealer() throws AlreadyExistsException, NotFoundException {
        CarDealer carDealer = new CarDealer();
        carDealer.setId(1);
        carDealer.setEmail("test@test.com");
        when(carDealerRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(carDealerRepository.save(carDealer)).thenReturn(carDealer);
        CarDealer saved = carDealerService.saveCarDealer(carDealer);
        assertEquals(1, saved.getId());
        verify(carDealerRepository, times(1)).findByEmail("test@test.com");
        verify(carDealerRepository, times(1)).save(carDealer);  // Expect save() to be called once
    }


    @Test
    public void testSaveCarDealer_AlreadyExists() {
        CarDealer carDealer = new CarDealer();
        carDealer.setId(1);
        carDealer.setEmail("test@test.com");
        when(carDealerRepository.findByEmail("test@test.com")).thenReturn(Optional.of(carDealer));
        assertThrows(AlreadyExistsException.class, () -> carDealerService.saveCarDealer(carDealer));
        verify(carDealerRepository, times(1)).findByEmail("test@test.com");
    }
}

