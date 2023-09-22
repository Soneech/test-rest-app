package org.soneech.springcourse.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
