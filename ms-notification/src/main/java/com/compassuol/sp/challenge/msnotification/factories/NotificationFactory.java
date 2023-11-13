package com.compassuol.sp.challenge.msnotification.factories;

import com.compassuol.sp.challenge.msnotification.dtos.NotificationDTO;
import com.compassuol.sp.challenge.msnotification.entities.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NotificationFactory {

    public static Notification createNotification(NotificationDTO notificationDTO) throws ParseException {
        Notification notification = new Notification();
        notification.setEvent(notificationDTO.getEvent());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        var dateFormated = dateFormat.parse(notificationDTO.getDate());
        notification.setDate(dateFormated);
        notification.setEmail(notificationDTO.getEmail());
        return notification;
    }
}
