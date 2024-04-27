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

import com.cts.thundercars.entity.Notification;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.NotificationRepository;

public class NotificationServicesImplTest {

    @InjectMocks
    private NotificationServicesImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNotification() throws AlreadyExistsException {
        Notification notification = new Notification();
        notification.setId(1);
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());
        when(notificationRepository.save(notification)).thenReturn(notification);
        Notification createdNotification = notificationService.createNotification(notification);
        assertEquals(1, createdNotification.getId());
        verify(notificationRepository, times(1)).findById(1);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    public void testCreateNotification_AlreadyExists() {
        Notification notification = new Notification();
        notification.setId(1);
        when(notificationRepository.findById(1)).thenReturn(Optional.of(notification));
        assertThrows(AlreadyExistsException.class, () -> notificationService.createNotification(notification));
        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testGetNotificationById() throws NotFoundException {
        Notification notification = new Notification();
        notification.setId(1);
        when(notificationRepository.findById(1)).thenReturn(Optional.of(notification));
        Notification foundNotification = notificationService.getNotificationById(1);
        assertEquals(1, foundNotification.getId());
        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testGetNotificationById_NotFound() {
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> notificationService.getNotificationById(1));
        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAllNotifications() throws NotFoundException {
        when(notificationRepository.findAll()).thenReturn(Arrays.asList(new Notification(), new Notification()));
        List<Notification> notifications = notificationService.getAllNotifications();
        assertEquals(2, notifications.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllNotifications_NotFound() {
        when(notificationRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> notificationService.getAllNotifications());
        verify(notificationRepository, times(1)).findAll();
    }
}
