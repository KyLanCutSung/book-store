package com.mingming.bookservice.command.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCreatedEvent {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
