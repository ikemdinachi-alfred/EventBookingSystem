package com.alfredTech.eventBookingManagementApplication.controller;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.LoginResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.RegistrationResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidDetailsException;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidPasswordException;
import com.alfredTech.eventBookingManagementApplication.exceptions.NotAValidEmailException;
import com.alfredTech.eventBookingManagementApplication.exceptions.UserExistException;
import com.alfredTech.eventBookingManagementApplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public RegistrationResponse registerUser(@RequestBody RegistrationRequest registrationRequest) {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setMessage("registration successful");
        try {
           return userService.registerUser(registrationRequest);
       }
       catch (UserExistException | InvalidPasswordException | NotAValidEmailException exception) {
           registrationResponse.setMessage(exception.getMessage());
           return registrationResponse;
       }
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("login successful");
        try {
            return userService.loginUser(loginRequest);
        } catch (InvalidDetailsException exception) {
            loginResponse.setMessage(exception.getMessage());
            return loginResponse;
        }

    }
    @GetMapping("/view_all")
    public Iterable<User> viewAllUsers() {
        return userService.getAllUsers();
    }

    }
