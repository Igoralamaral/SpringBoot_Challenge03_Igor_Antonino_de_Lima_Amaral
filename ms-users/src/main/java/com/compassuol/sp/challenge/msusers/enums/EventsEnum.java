package com.compassuol.sp.challenge.msusers.enums;

import lombok.*;

public enum EventsEnum {

    CREATE("CREATE"), LOGIN("LOGIN"), UPDATE("UPDATE"), UPDATE_PASSWORD("UPDATE_PASSWORD");
    private String event;

    EventsEnum(String event){
        this.event = event;
    }

    public String getEvent(){
        return event;
    }

}
