package com.cts.thundercars.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.thundercars.entity.Notification;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.NotificationRepository;
import com.cts.thundercars.services.service.NotificationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationServicesImpl implements NotificationServices{
	
	private final NotificationRepository notificationRepository;
	
	@Autowired
	public NotificationServicesImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	@Override
	public Notification createNotification(Notification notification) throws AlreadyExistsException {
		log.info("Creating new Notification");
		Optional<Notification> optionalNotification = notificationRepository.findById(notification.getId());
		if(optionalNotification.isEmpty()) {
			Notification createdNotification = notificationRepository.save(notification);
			log.info("notification created successfully");
			return createdNotification;
		}
		log.error("Notification with this id already created");
		throw new AlreadyExistsException("Notification with this id already created before");
	}

	public Notification getNotificationById(Integer id) throws NotFoundException {
	    log.info("fetch notification with this id: {}", id);
	    Optional<Notification> optionalNotification = notificationRepository.findById(id);
	    if(optionalNotification.isPresent()) {
	        Notification notification = optionalNotification.get();
	        log.info("notification fetched");
	        return notification; 
	    }
	    log.error("Notification with this id does not exist");
	    throw new NotFoundException("Notification with this id can not be found");
	}


	@Override
	public List<Notification> getAllNotifications() throws NotFoundException {

		log.info("Get all notifications");
		List<Notification> notifications = notificationRepository.findAll();
		if(!notifications.isEmpty()) {
			log.info("notification fetched successfully");
			return notifications;
		}
		log.error("Notification is empty");
		throw new NotFoundException("Notification is empty");
	}


	@Override
	public String deleteNotification(Integer id) throws NotFoundException {
		log.info("check if the notification with this id exists");
		Boolean isExists= notificationRepository.existsById(id);
		if(isExists) {
			notificationRepository.delete(notificationRepository.findById(id).get());
			log.info("notification deleted successfully");
			return "notification deleted successfully";
		}
		throw new NotFoundException("Notification can not be found with this id");
		
	}

	@Override
	public List<Notification> getNotificationsByUserId(Integer userId) throws NotFoundException {
		log.info("fetching notifications");
		List<Notification> notifications = notificationRepository.findByUserId(userId);
		if(!notifications.isEmpty()) {
			log.info("notifications fetched");
			return notifications;
		}
		log.error("No notifications found");
		throw new NotFoundException("No notifications found");
	}

	@Override
	public List<Notification> getUnreadNotificationsByUserId(Integer userId) throws NotFoundException {
		
		log.info("fetching notifications");
		List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
		if(!notifications.isEmpty()) {
			log.info("notifications fetched");
			return notifications;
		}
		log.error("No notifications found");
		throw new NotFoundException("No notifications found");
	}

}
