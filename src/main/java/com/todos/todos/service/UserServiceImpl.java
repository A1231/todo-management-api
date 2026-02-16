package com.todos.todos.service;

import com.todos.todos.entity.Authority;
import com.todos.todos.entity.User;
import com.todos.todos.repository.UserRepository;
import com.todos.todos.request.PasswordUpdateRequest;
import com.todos.todos.response.UserResponse;
import com.todos.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {


        User user = findAuthenticatedUser.getAuthenticatedUser();
        return new UserResponse(
                user.getId(), user.getFirstName()+" "+user.getLastName(), user.getEmail(),
                user.getAuthorities().stream().map(auth->(Authority)auth).toList()
                );
    }

    @Override
    public void deleteUser() {


        User user =  findAuthenticatedUser.getAuthenticatedUser();

        // isLastAdmin
        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete themselves");
        }


        userRepository.delete(user);

    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        if(!isOldPasswordCorrect(user.getPassword(), passwordUpdateRequest.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current Password is incorrect");
        }

        if(!isNewPasswordCorrect(passwordUpdateRequest.getNewPassword(), passwordUpdateRequest.getNewPassword2())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New Passwords does not match");
        }
        if(!isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword() )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New Password must be different from the current passowrd");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
    }

    private boolean isOldPasswordCorrect(String currentPassword, String oldPassword){
        return passwordEncoder.matches(oldPassword, currentPassword);
    }

    private boolean isNewPasswordCorrect(String newPassword, String newPasswordConfirmation){
        return newPasswordConfirmation.equals(newPassword);
    }

    private boolean isNewPasswordDifferent(String oldPassword, String newPassword){
        return !oldPassword.equals(newPassword);
    }

    private boolean isLastAdmin(User user){
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if(isAdmin){
            long adminCount = userRepository.countAdminUsers();
            return adminCount <=1;
        }
        return false;
    }


}
