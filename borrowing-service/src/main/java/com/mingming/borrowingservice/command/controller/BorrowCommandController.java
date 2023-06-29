package com.mingming.borrowingservice.command.controller;

import com.mingming.borrowingservice.command.command.CreateBorrowCommand;
import com.mingming.borrowingservice.command.command.UpdateBookReturnCommand;
import com.mingming.borrowingservice.command.model.BorrowRequestModel;
import com.mingming.borrowingservice.command.service.BorrowService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Source;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
@EnableBinding(Source.class)
public class BorrowCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private BorrowService borrowService;
    @PostMapping
    public String addBookBorrowing(@RequestBody BorrowRequestModel model){
        try{
            CreateBorrowCommand command =
                    new CreateBorrowCommand(UUID.randomUUID().toString(), model.getBookId(), model.getEmployeeId(), new Date());
            commandGateway.sendAndWait(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Book borrowing added";
    }

    @PutMapping
    public String updateBookReturn(@RequestBody BorrowRequestModel model){
        UpdateBookReturnCommand command = new UpdateBookReturnCommand(
                borrowService.findBorrowId(model.getEmployeeId(), model.getBookId()), model.getBookId(), model.getEmployeeId(), new Date()
        );
        commandGateway.sendAndWait(command);
        return "Book returned";
    }
}
