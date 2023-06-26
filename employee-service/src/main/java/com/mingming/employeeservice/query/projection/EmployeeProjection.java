package com.mingming.employeeservice.query.projection;

import com.mingming.commonservice.model.EmployeeResponseCommonModel;
import com.mingming.commonservice.query.GetDetailsEmployeeQuery;
import com.mingming.employeeservice.command.data.Employee;
import com.mingming.employeeservice.command.data.EmployeeRepository;
import com.mingming.employeeservice.query.model.EmployeeResponseModel;
import com.mingming.employeeservice.query.queries.GetAllEmployeeQuery;
import com.mingming.employeeservice.query.queries.GetEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeQuery getEmployeesQuery) {
        EmployeeResponseModel model = new EmployeeResponseModel();
        Employee employee = employeeRepository.getReferenceById(getEmployeesQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);

        return model;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery getAllEmployeeQuery){
        List<EmployeeResponseModel> listModel = new ArrayList<>();
        List<Employee> listEntity = employeeRepository.findAll();
        listEntity.stream().forEach(s -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(s, model);
            listModel.add(model);
        });
        return listModel;
    }

    @QueryHandler
    public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery query){
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        Employee employee = employeeRepository.getById(query.getEmployeeId());
        BeanUtils.copyProperties(employee, model);
        return model;
    }
}
