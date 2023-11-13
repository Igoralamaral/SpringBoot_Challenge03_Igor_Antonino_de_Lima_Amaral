package com.compassuol.sp.challenge.msusers.dtos;

import com.compassuol.sp.challenge.msusers.enums.EventsEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO implements Serializable {

    private String email;
    private EventsEnum event;
    private String date;
}
