package com.mingming.commonservice.events;

import lombok.Data;

@Data
public class BookUpdateStatusEvent {
    private String bookId;
    private String employeeId;
    private String borrowId;
    private Boolean isReady;
}
