package com.mingming.bookservice.command.events;

import com.mingming.bookservice.command.data.Book;
import com.mingming.bookservice.command.data.BookRepository;
import com.mingming.commonservice.events.BookRollBackStatusEvent;
import com.mingming.commonservice.events.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event){
        Book book = bookRepository.getReferenceById(event.getBookId());
        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookDeletedEvent event){
        bookRepository.deleteById(event.getBookId());
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event){
        Book book = bookRepository.getById(event.getBookId());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookRollBackStatusEvent event){
        Book book = bookRepository.getReferenceById(event.getBookId());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
}
