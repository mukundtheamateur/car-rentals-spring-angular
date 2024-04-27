package com.cts.thundercars.repository;

import com.cts.thundercars.entity.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	List<Notification> findByUserId(Integer userId);
	List<Notification> findByUserIdAndIsReadFalse(Integer userId);
}
