package com.mingming.borrowingservice.command.service;

import com.mingming.borrowingservice.command.model.Message;

public interface BorrowService {
    void sendMessage(Message message);
    String findBorrowId(String employeeId, String bookId);
}
