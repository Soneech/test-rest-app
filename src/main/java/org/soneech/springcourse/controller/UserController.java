package org.soneech.springcourse.controller;

import org.soneech.springcourse.model.User;
import org.soneech.springcourse.service.UserService;
import org.soneech.springcourse.util.UserErrorResponse;
import org.soneech.springcourse.util.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();  // Jackson converts objects to JSON
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exception) {
        UserErrorResponse response = new UserErrorResponse(
                "User with this id wasn't found",
                System.currentTimeMillis()
        );
        // Response body - response (json), in header - status (NOT_FOUND)
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 status
    }
}
