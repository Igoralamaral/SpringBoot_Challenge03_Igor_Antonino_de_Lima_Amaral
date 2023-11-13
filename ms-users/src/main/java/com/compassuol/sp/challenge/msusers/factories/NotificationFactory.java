package com.compassuol.sp.challenge.msusers.factories;

import com.compassuol.sp.challenge.msusers.dtos.NotificationDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationFactory {

    public static NotificationDTO createNotification(String email, String event, Date date){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(email);
        notificationDTO.setEvent(event);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormated = dateFormat.format(date);
        notificationDTO.setDate(dateFormated);
        return notificationDTO;
    }
}
