package com.mingming.employeeservice.command.events;

import lombok.Data;

@Data
public class EmployeeDeletedEvent {
    private String employeeId;
}
