package com.todos.todos.service;

import com.todos.todos.request.AuthenticationRequest;
import com.todos.todos.request.RegisterRequest;
import com.todos.todos.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest input) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request);
}
