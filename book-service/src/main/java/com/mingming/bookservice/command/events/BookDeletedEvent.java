package com.mingming.bookservice.command.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDeletedEvent {
    private String bookId;
}
