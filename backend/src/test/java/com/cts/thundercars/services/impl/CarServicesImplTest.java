package com.cts.thundercars.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.thundercars.entity.Car;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.CarRepository;

public class CarServicesImplTest {

    @InjectMocks
    private CarServicesImpl carService;

    @Mock
    private CarRepository carRepository;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCars() throws NotFoundException {
        when(carRepository.findAll()).thenReturn(Arrays.asList(new Car(), new Car()));
        List<Car> cars = carService.getAllCars();
        assertEquals(2, cars.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCars_NotFound() {
        when(carRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> carService.getAllCars());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void testGetCarById() throws NotFoundException {
        Car car = new Car();
        car.setId(1);
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        Car found = carService.getCarById(1);
        assertEquals(1, found.getId());
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCarById_NotFound() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> carService.getCarById(1));
        verify(carRepository, times(1)).findById(1);
    }
}

