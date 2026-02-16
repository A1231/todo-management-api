package com.todos.todos.service;


import com.todos.todos.request.PasswordUpdateRequest;
import com.todos.todos.response.UserResponse;



public interface UserService {

    UserResponse getUserInfo();
    void deleteUser();

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
