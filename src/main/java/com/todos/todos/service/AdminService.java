package com.todos.todos.service;

import com.todos.todos.response.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();
    UserResponse promoteToAdmin(long userId);
    void deleteNonAdminUser(long userId);
}
