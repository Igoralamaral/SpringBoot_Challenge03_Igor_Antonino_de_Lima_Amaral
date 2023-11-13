package com.compassuol.sp.challenge.msnotification.repositories;

import com.compassuol.sp.challenge.msnotification.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
}
