package com.mingming.borrowingservice.command.events;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateBookReturnEvent {
    private String id;
    private String bookId;
    private String employeeId;
    private Date returnDate;
}
