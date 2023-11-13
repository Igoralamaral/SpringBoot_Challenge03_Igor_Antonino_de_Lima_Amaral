package com.compassuol.sp.challenge.msnotification.factories;

import com.compassuol.sp.challenge.msnotification.dtos.NotificationDTO;
import com.compassuol.sp.challenge.msnotification.entities.Notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationFactory {

    public static Notification createNotification(NotificationDTO notificationDTO) throws ParseException {
        Notification notification = new Notification();
        notification.setEvent(notificationDTO.getEvent());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        var date = dateFormat.parse(notificationDTO.getDate());
        notification.setDate(date);
        notification.setEmail(notificationDTO.getEmail());
        return notification;
    }
}
