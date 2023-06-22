package com.mingming.bookservice.query.queries;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GetAllBooksQuery {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
