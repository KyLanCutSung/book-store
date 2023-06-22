package com.mingming.bookservice.command.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookUpdatedEvent {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
