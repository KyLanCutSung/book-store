package com.mingming.borrowingservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, String> {
    List<Borrowing> findByEmployeeIdAndReturnDateIsNull(String employeeId);
    Borrowing findByEmployeeIdAndBookIdAndReturnDateIsNull(String employeeId, String bookId);
}
