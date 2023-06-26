package com.mingming.borrowingservice.command.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingming.borrowingservice.command.data.BorrowingRepository;
import com.mingming.borrowingservice.command.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;

@Service
@EnableBinding(Source.class)
public class BorrowService implements BorrowServiceImpl{
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Qualifier("errorChannel")
    @Autowired
    private MessageChannel output;

    @Override
    public void sendMessage(Message message){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(mapper);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findBorrowId(String employeeId, String bookId){
        return borrowingRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId,bookId).getId();
    }

}
