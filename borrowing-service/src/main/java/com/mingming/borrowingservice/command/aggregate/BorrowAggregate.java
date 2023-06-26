package com.mingming.borrowingservice.command.aggregate;

import com.mingming.borrowingservice.command.command.CreateBorrowCommand;
import com.mingming.borrowingservice.command.command.DeleteBorrowCommand;
import com.mingming.borrowingservice.command.command.SendMessageCommand;
import com.mingming.borrowingservice.command.command.UpdateBookReturnCommand;
import com.mingming.borrowingservice.command.events.BorrowCreatedEvent;
import com.mingming.borrowingservice.command.events.BorrowDeletedEvent;
import com.mingming.borrowingservice.command.events.BorrowSendMessageEvent;
import com.mingming.borrowingservice.command.events.UpdateBookReturnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class BorrowAggregate {
    @AggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private String message;

    @CommandHandler
    public BorrowAggregate(CreateBorrowCommand command){
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteBorrowCommand command){
        BorrowDeletedEvent event = new BorrowDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(SendMessageCommand command){
        BorrowSendMessageEvent event = new BorrowSendMessageEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateBookReturnCommand command){
        UpdateBookReturnEvent event = new UpdateBookReturnEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreatedEvent event){
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();
    }

    @EventSourcingHandler
    public void on(BorrowDeletedEvent event){
        this.id = event.getId();
    }

    @EventSourcingHandler
    public void on(BorrowSendMessageEvent event){
        this.id = event.getId();
        this.message = event.getMessage();
        this.employeeId = event.getEmployeeId();
    }

    @EventSourcingHandler
    public void on(UpdateBookReturnEvent event){
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
    }
}
