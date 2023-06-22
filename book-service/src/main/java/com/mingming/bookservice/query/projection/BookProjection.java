package com.mingming.bookservice.query.projection;

import com.mingming.bookservice.command.data.Book;
import com.mingming.bookservice.command.data.BookRepository;
import com.mingming.bookservice.query.model.BookResponseModel;
import com.mingming.bookservice.query.queries.GetAllBooksQuery;
import com.mingming.bookservice.query.queries.GetBooksQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBooksQuery getBooksQuery){
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.getReferenceById(getBooksQuery.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery){
        List<Book> bookList = bookRepository.findAll();
        List<BookResponseModel> list = new ArrayList<>();
        bookList.forEach(s -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(s, model);
            list.add(model);
        });
        return list;
    }
}
