package com.compassuol.sp.challenge.msnotification.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String email;
    private String event;
    private Date date;
}
