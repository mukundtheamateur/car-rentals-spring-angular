package com.cts.thundercars.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.CarDealerServices;

public class CarDealerControllerTest {

    @InjectMocks
    CarDealerController carDealerController;

    @Mock
    CarDealerServices carDealerServices;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllCarDealer() throws NotFoundException {
        when(carDealerServices.getAllCarDealers()).thenReturn(new ResponseEntity<>(Arrays.asList(new CarDealer()), HttpStatus.OK));
        ResponseEntity<List<CarDealer>> response = carDealerController.findAllCarDealer();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetCarDealerById() throws NotFoundException {
        CarDealer carDealer = new CarDealer();
        when(carDealerServices.getCarDealerById(anyInt())).thenReturn(carDealer);
        ResponseEntity<CarDealer> response = carDealerController.getCarDealerById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carDealer, response.getBody());
    }

    @Test
    public void testCreateCarDealer() throws AlreadyExistsException, NotFoundException {
        CarDealer carDealer = new CarDealer();
        when(carDealerServices.saveCarDealer(any(CarDealer.class))).thenReturn(carDealer);
        ResponseEntity<CarDealer> response = carDealerController.createCarDealer(carDealer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(carDealer, response.getBody());
    }

    @Test
    public void testDeleteCarDealer() throws NotFoundException {
        // Given
        Integer id = 1;
        String expectedResponse = "CarDealer deleted successfully";

        // When
        Mockito.doAnswer(invocation -> {
            // You can add any logic you want to run when the method is called
            return null;
        }).when(carDealerServices).deleteCarDealer(anyInt());

        // Then
        ResponseEntity<String> response = carDealerController.deleteCarDealer(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(carDealerServices, times(1)).deleteCarDealer(anyInt());
    }






    @Test
    public void testUpdateCarDealer() throws NotFoundException {
        CarDealer carDealer = new CarDealer();
        when(carDealerServices.updateCarDealer(any(CarDealer.class))).thenReturn(carDealer);
        CarDealer response = carDealerController.updateCar(carDealer);
        assertEquals(carDealer, response);
    }
}
