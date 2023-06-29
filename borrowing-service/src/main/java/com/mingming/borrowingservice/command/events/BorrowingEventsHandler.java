package com.mingming.borrowingservice.command.events;

import com.mingming.borrowingservice.command.data.Borrowing;
import com.mingming.borrowingservice.command.data.BorrowingRepository;
import com.mingming.borrowingservice.command.model.Message;
import com.mingming.borrowingservice.command.service.BorrowService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventsHandler {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BorrowService borrowService;

    @EventHandler
    public void on(BorrowCreatedEvent event){
        Borrowing model = new Borrowing();
        BeanUtils.copyProperties(event, model);
        borrowingRepository.save(model);
    }

    @EventHandler
    public void on(BorrowDeletedEvent event){
        if(borrowingRepository.findById(event.getId()).isPresent()){
            borrowingRepository.deleteById(event.getId());
        }
        else return;
    }

    @EventHandler
    public void on(BorrowSendMessageEvent event){
        Message message = new Message(event.getEmployeeId(), event.getMessage());
        borrowService.sendMessage(message);
    }

    @EventHandler
    public void on(UpdateBookReturnEvent event){
        Borrowing model = borrowingRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(
                event.getEmployeeId(), event.getBookId()
        );
        model.setReturnDate(event.getReturnDate());
        borrowingRepository.save(model);
    }
}
