package org.soneech.springcourse.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserErrorResponse {
    private String message;
    private Date timestamp;
}
