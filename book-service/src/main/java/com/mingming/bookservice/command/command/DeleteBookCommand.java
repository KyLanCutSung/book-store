package com.mingming.bookservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
}
