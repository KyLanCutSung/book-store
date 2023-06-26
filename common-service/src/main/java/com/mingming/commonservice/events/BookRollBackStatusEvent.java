package com.mingming.commonservice.events;

import lombok.Data;

@Data
public class BookRollBackStatusEvent {
    private String bookId;
    private Boolean isReady;
    private String EmployeeId;
    private String borrowId;
}
