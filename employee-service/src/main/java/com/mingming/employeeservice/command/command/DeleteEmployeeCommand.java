package com.mingming.employeeservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteEmployeeCommand {
    private String employeeId;
}
