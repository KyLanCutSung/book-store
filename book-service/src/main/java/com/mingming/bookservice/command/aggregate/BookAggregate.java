package com.mingming.bookservice.command.aggregate;

import com.mingming.bookservice.command.command.CreateBookCommand;
import com.mingming.bookservice.command.command.DeleteBookCommand;
import com.mingming.bookservice.command.command.UpdateBookCommand;
import com.mingming.bookservice.command.events.BookCreatedEvent;
import com.mingming.bookservice.command.events.BookDeletedEvent;
import com.mingming.bookservice.command.events.BookUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate(){

    }

    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(command, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand command){
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(command, bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand command){
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(command, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.name = event.getName();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.name = event.getName();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event){
        this.bookId = event.getBookId();
    }
}
