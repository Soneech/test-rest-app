package org.soneech.springcourse.exception;

public class UserNotCreatedOrUpdatedException extends RuntimeException {
    public UserNotCreatedOrUpdatedException(String message) {
        super(message);
    }
}
