package com.mingming.employeeservice.command.events;

import lombok.Data;

@Data
public class EmployeeUpdatedEvent {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;
}
