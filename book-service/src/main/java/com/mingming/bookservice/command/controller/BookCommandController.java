package com.mingming.bookservice.command.controller;

import com.mingming.bookservice.command.command.CreateBookCommand;
import com.mingming.bookservice.command.command.DeleteBookCommand;
import com.mingming.bookservice.command.command.UpdateBookCommand;
import com.mingming.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model){
        CreateBookCommand command =
                new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
        String result = commandGateway.sendAndWait(command);
        return result;
    }
    @PutMapping
    public String updateBook(@RequestBody BookRequestModel model){
        UpdateBookCommand command =
                new UpdateBookCommand(model.getBookId(), model.getName(), model.getAuthor(), model.getIsReady());
        commandGateway.sendAndWait(command);
        return "updated book";
    }
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable String bookId){
        commandGateway.sendAndWait(new DeleteBookCommand(bookId));
        return "delete book";
    }
}
