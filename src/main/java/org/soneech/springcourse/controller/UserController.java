package org.soneech.springcourse.controller;

import jakarta.validation.Valid;
import org.soneech.springcourse.model.User;
import org.soneech.springcourse.service.UserService;
import org.soneech.springcourse.util.UserErrorResponse;
import org.soneech.springcourse.util.UserNotCreatedException;
import org.soneech.springcourse.util.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PostMapping
    public ResponseEntity<HttpStatus> saveUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (var error: errors) {
                errorMessage.append(error.getField())
                        .append(":").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMessage.toString());
        }

        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exception) {
        UserErrorResponse response = new UserErrorResponse(
                "User with this id wasn't found",
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException exception) {
        UserErrorResponse response = new UserErrorResponse(
                exception.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
