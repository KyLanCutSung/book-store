package com.mingming.borrowingservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String employeeId;
    private String message;

    @Override
    public String toString() {
        return "Message [employeeId= " + employeeId + ", message= " + message + "]";
    }
}
