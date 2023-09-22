package org.soneech.springcourse.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.soneech.springcourse.dto.UserDTO;
import org.soneech.springcourse.model.User;
import org.soneech.springcourse.service.UserService;
import org.soneech.springcourse.api.response.UserErrorResponse;
import org.soneech.springcourse.exception.UserNotCreatedOrUpdatedException;
import org.soneech.springcourse.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findAll()
                .stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return convertToUserDTO(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new UserNotCreatedOrUpdatedException(getFieldsErrorMessage(bindingResult));

        userService.save(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exception) {
        return new ResponseEntity<>(
                createUserErrorResponse(exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedOrUpdatedException exception) {
        return new ResponseEntity<>(
                createUserErrorResponse(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private String getFieldsErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (var error: errors) {
            errorMessage.append(error.getField())
                    .append(":").append(error.getDefaultMessage())
                    .append(";");
        }

        return errorMessage.toString();
    }

    private UserErrorResponse createUserErrorResponse(String errorMessage) {
        return new UserErrorResponse(errorMessage, LocalDateTime.now());
    }
}
