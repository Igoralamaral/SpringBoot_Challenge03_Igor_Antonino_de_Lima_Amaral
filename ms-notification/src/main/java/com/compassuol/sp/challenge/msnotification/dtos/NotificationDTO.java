package com.compassuol.sp.challenge.msnotification.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO implements Serializable {

    private String email;
    private String event;
    private String date;
}
