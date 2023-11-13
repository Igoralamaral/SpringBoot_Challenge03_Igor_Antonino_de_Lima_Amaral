package com.compassuol.sp.challenge.msusers.factories;

import com.compassuol.sp.challenge.msusers.dtos.NotificationDTO;
import com.compassuol.sp.challenge.msusers.enums.EventsEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationFactory {

    public static NotificationDTO createNotification(String email, EventsEnum event, Date date){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(email);
        notificationDTO.setEvent(event);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        var dateFormated = dateFormat.format(date);
        notificationDTO.setDate(dateFormated);
        return notificationDTO;
    }
}
