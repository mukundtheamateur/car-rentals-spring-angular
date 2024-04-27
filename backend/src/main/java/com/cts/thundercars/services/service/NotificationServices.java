package com.cts.thundercars.services.service;

import java.util.List;
import com.cts.thundercars.entity.Notification;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;

public interface NotificationServices {
    Notification createNotification(Notification notification) throws AlreadyExistsException;
    Notification getNotificationById(Integer id) throws NotFoundException;
    List<Notification> getAllNotifications() throws NotFoundException;
    String deleteNotification(Integer id) throws NotFoundException;
    List<Notification> getNotificationsByUserId(Integer userId) throws NotFoundException;
    List<Notification> getUnreadNotificationsByUserId(Integer userId) throws NotFoundException;
}
