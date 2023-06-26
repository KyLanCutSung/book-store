package com.mingming.borrowingservice.command.saga;

import com.mingming.borrowingservice.command.command.DeleteBorrowCommand;
import com.mingming.borrowingservice.command.command.SendMessageCommand;
import com.mingming.borrowingservice.command.events.BorrowCreatedEvent;
import com.mingming.borrowingservice.command.events.BorrowDeletedEvent;
import com.mingming.borrowingservice.command.events.UpdateBookReturnEvent;
import com.mingming.commonservice.command.RollBackStatusBookCommand;
import com.mingming.commonservice.command.UpdateStatusBookCommand;
import com.mingming.commonservice.events.BookRollBackStatusEvent;
import com.mingming.commonservice.events.BookUpdateStatusEvent;
import com.mingming.commonservice.model.BookResponseCommonModel;
import com.mingming.commonservice.model.EmployeeResponseCommonModel;
import com.mingming.commonservice.query.GetDetailBookQuery;
import com.mingming.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event){
        System.out.println("BorrowCreatedEvent in Saga for BookId : "+event.getBookId()+ " : EmployeeId :  "+event.getEmployeeId());
        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());
            GetDetailBookQuery getDetailBookQuery = new GetDetailBookQuery(event.getBookId());
            BookResponseCommonModel bookResponseCommonModel =
                    queryGateway.query(getDetailBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join();
            if(bookResponseCommonModel.getIsReady() == true) {
                UpdateStatusBookCommand command =
                        new UpdateStatusBookCommand(event.getBookId(), event.getEmployeeId(), event.getId(), false);
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("Someone had borrow this book!");
            }
        } catch (Exception e) {
            rollBackBorrowRecord(event.getId());
            System.out.println(e.getMessage());
        }
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(UpdateBookReturnEvent event){
        System.out.println("UpdateBookReturnEvent in Saga for borrowId: "+event.getId());
        try{
            UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(),event.getEmployeeId(),event.getId(), true);
            commandGateway.sendAndWait(command);
            commandGateway.sendAndWait(new SendMessageCommand(event.getId(), event.getEmployeeId(), "Da tra sach thanh cong"));
            SagaLifecycle.end();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event) {
        System.out.println("BookUpdateStatusEvent in Saga for BookId : "+event.getBookId());
        try {
            GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(event.getEmployeeId());

            EmployeeResponseCommonModel employeeResponseCommonModel =
                    queryGateway.query(getDetailsEmployeeQuery,
                            ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                            .join();
            if(employeeResponseCommonModel.getIsDisciplined()==true) {
                throw new Exception("Nhan vien bi ky luat");
            }else {
                commandGateway.sendAndWait(new SendMessageCommand(event.getBorrowId(), event.getEmployeeId(), "Da muon sach thanh cong !"));
                SagaLifecycle.end();
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
            rollBackBookStatus(event.getBookId(),event.getEmployeeId(),event.getBorrowId());
        }
    }
    @SagaEventHandler(associationProperty = "bookId")
    public void handleRollBackBookStatus(BookRollBackStatusEvent event){
        System.out.println("BookRollBackStatusEvent is Saga for bookId: {}"+event.getBookId());
        rollBackBorrowRecord(event.getBorrowId());
    }

    private void rollBackBorrowRecord(String id){
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowId){
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId, true, employeeId, borrowId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BorrowDeletedEvent event){
        System.out.println("BorrowDeletedEvent in Saga for BorrowingId: {}"+event.getId());
        SagaLifecycle.end();
    }
}
