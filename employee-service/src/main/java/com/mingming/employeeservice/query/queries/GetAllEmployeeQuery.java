package com.mingming.employeeservice.query.queries;

import lombok.Data;

@Data
public class GetAllEmployeeQuery {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

}
