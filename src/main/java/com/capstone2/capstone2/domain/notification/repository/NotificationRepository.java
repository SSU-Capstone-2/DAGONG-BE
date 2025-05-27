package com.capstone2.capstone2.domain.notification.repository;

import com.capstone2.capstone2.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
