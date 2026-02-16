package com.todos.todos.service;

import com.todos.todos.entity.Authority;
import com.todos.todos.entity.User;
import com.todos.todos.repository.UserRepository;
import com.todos.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::convertToUserResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse promoteToAdmin(long userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty() && user.get().getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist or already an Admin");
        }

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        authorities.add(new Authority("ROLE_ADMIN"));
        user.get().setAuthorities(authorities);

        User savedUser = userRepository.save(user.get());
        return convertToUserResponse(savedUser);

    }

    @Override
    @Transactional
    public void deleteNonAdminUser(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty() && user.get().getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist or already an Admin");
        }

        userRepository.delete(user.get());

    }

    private UserResponse convertToUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFirstName()+" "+user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority)auth).toList()
        );
    }

}
