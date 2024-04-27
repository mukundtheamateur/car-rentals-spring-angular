package com.cts.thundercars.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.ResponseEntity;

import com.cts.thundercars.entity.Notification;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.NotificationServices;

public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationServices notificationServices;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testCreateNotification() throws AlreadyExistsException {
        Notification notification = new Notification();
        notification.setId(1);
        when(notificationServices.createNotification(notification)).thenReturn(notification);
        ResponseEntity<Notification> response = notificationController.createNotification(notification);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
        verify(notificationServices, times(1)).createNotification(notification);
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testGetNotificationById() throws NotFoundException {
        Notification notification = new Notification();
        notification.setId(1);
        when(notificationServices.getNotificationById(1)).thenReturn(notification);
        ResponseEntity<Notification> response = notificationController.getNotificationById(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
        verify(notificationServices, times(1)).getNotificationById(1);
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testGetAllNotifications() throws NotFoundException {
        when(notificationServices.getAllNotifications()).thenReturn(Arrays.asList(new Notification(), new Notification()));
        ResponseEntity<List<Notification>> response = notificationController.getAllNotifications();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(notificationServices, times(1)).getAllNotifications();
    }
}
