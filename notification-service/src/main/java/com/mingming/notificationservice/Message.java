package com.mingming.notificationservice;

import lombok.Data;

@Data
public class Message {
    private String employeeId;
    private String message;

    @Override
    public String toString(){
        return "Message [employeeId=" + employeeId +", message=" + message +"]";
    }
}
