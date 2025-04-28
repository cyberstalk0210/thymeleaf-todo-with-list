package org.example.model;

import lombok.*;
import org.example.Enum.Priority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Todo {
    private int id;
    private String title;
    private Priority priority;
//    private boolean completed;
    private LocalDateTime createdAt = LocalDateTime.now();
}
