package com.mingming.bookservice.query.queries;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GetBooksQuery {
    private String bookId;
}
