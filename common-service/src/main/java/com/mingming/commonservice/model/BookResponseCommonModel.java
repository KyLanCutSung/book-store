package com.mingming.commonservice.model;

import lombok.Data;

@Data
public class BookResponseCommonModel {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

}
