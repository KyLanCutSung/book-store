package com.mingming.borrowingservice.query.projection;

import com.mingming.borrowingservice.command.data.Borrowing;
import com.mingming.borrowingservice.command.data.BorrowingRepository;
import com.mingming.borrowingservice.query.model.BorrowingResponseModel;
import com.mingming.borrowingservice.query.queries.GetAllBorrowing;
import com.mingming.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.mingming.commonservice.model.BookResponseCommonModel;
import com.mingming.commonservice.model.EmployeeResponseCommonModel;
import com.mingming.commonservice.query.GetDetailBookQuery;
import com.mingming.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingProjection {
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetListBorrowingByEmployeeQuery query){
        List<BorrowingResponseModel> list = new ArrayList<>();
        List<Borrowing> listEntity = borrowingRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId());
        listEntity.forEach(s -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(s, model);
            model.setNameBook(queryGateway.query(new GetDetailBookQuery(model.getBookId()), ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join().getName());
            EmployeeResponseCommonModel employee = queryGateway.query(new GetDetailsEmployeeQuery(model.getEmployeeId()), ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                    .join();
            model.setNameEmployee(employee.getFirstName()+ " " +employee.getLastName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetAllBorrowing query){
        List<BorrowingResponseModel> list = new ArrayList<>();
        List<Borrowing> listEntity = borrowingRepository.findAll();
        listEntity.forEach(s -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(s, model);
            model.setNameBook(queryGateway.query(new GetDetailBookQuery(model.getBookId()), ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join().getName());
            EmployeeResponseCommonModel employee = queryGateway.query(new GetDetailsEmployeeQuery(model.getEmployeeId()), ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                    .join();
            model.setNameEmployee(employee.getFirstName()+ " " +employee.getLastName());
            list.add(model);
        });
        return list;
    }

}
