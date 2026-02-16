package com.todos.todos.controller;

import com.todos.todos.entity.User;
import com.todos.todos.request.PasswordUpdateRequest;
import com.todos.todos.response.UserResponse;
import com.todos.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name="User REST API Endpoints", description = "Operations related to info about current users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "get current user info", description ="get current user info" )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo(){
        return userService.getUserInfo();
    }

    @Operation(summary = "Delete User", description ="delete current user info" )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteUser(){
        userService.deleteUser();
    }

    @Operation(summary = "Password Update", description ="Change Password after verification" )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/password")
    public void passwordUpdate(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        userService.updatePassword(passwordUpdateRequest);
    }
}
