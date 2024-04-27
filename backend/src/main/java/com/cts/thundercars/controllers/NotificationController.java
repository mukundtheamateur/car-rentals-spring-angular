package com.cts.thundercars.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.thundercars.entity.Notification;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.services.service.NotificationServices;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	
	private final NotificationServices notificationServices;
	
	@Autowired
	public NotificationController(NotificationServices notificationServices) {
		this.notificationServices = notificationServices;
	}

	@PostMapping
	public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) throws AlreadyExistsException {
		Notification createdNotification = notificationServices.createNotification(notification);
		return ResponseEntity.ok(createdNotification);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) throws NotFoundException {
		Notification notification = notificationServices.getNotificationById(id);
		return ResponseEntity.ok(notification);
	}

	@GetMapping(value= {"", "/"})
	public ResponseEntity<List<Notification>> getAllNotifications() throws NotFoundException {
		List<Notification> notifications = notificationServices.getAllNotifications();
		return ResponseEntity.ok(notifications);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteNotification(@PathVariable Integer id) throws NotFoundException {
		String response = notificationServices.deleteNotification(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Integer userId) throws NotFoundException {
		List<Notification> notifications = notificationServices.getNotificationsByUserId(userId);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/user/{userId}/unread")
	public ResponseEntity<List<Notification>> getUnreadNotificationsByUserId(@PathVariable Integer userId) throws NotFoundException {
		List<Notification> notifications = notificationServices.getUnreadNotificationsByUserId(userId);
		return ResponseEntity.ok(notifications);
	}
}
