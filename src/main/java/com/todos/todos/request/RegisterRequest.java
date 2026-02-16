package com.todos.todos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotEmpty(message = "First Name is mandatory")
    @Size(min = 3, max= 30, message="Atleast 3 characters")
    private String firstName;

    @NotEmpty(message = "last Name is mandatory")
    @Size(min = 3, max= 30, message="Atleast 3 characters")
    private String lastName;


    @NotEmpty(message = "email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 5, max= 30, message="Atleast 5 characters")
    private String password;

    public RegisterRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
