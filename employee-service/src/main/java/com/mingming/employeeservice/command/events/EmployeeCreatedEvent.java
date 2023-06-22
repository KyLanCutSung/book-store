package com.mingming.employeeservice.command.events;

import lombok.Data;

@Data
public class EmployeeCreatedEvent {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;
}
