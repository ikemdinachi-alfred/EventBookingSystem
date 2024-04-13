package com.alfredTech.eventBookingManagementApplication.services;

import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.data.repositories.UserRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.UpdateRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.LoginResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.RegistrationResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.UpdateResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidDetailsException;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidPasswordException;
import com.alfredTech.eventBookingManagementApplication.exceptions.NotAValidEmailException;
import com.alfredTech.eventBookingManagementApplication.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    @Autowired
    private UserRepository userRepository;

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        passwordValidation(registrationRequest.getPassword());
        if(userEmailExists(registrationRequest.getEmail()))
            throw  new UserExistException("User with "+registrationRequest.getEmail()+" already exist");;
        userEmailIsValid(registrationRequest.getEmail());
            user.setEnabled(false);
        userRepository.save(user);
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setMessage("registration successful");
        return registrationResponse;
    }
    private boolean userEmailExists(String email) {
        User foundMail = userRepository.findUserByEmail(email);
        return foundMail != null;
    }
    private void userEmailIsValid(String email) {
        if(!email.matches(EMAIL_REGEX)) throw
                new NotAValidEmailException("check "+ email + " is not a valid Email format ");
    }
    private void passwordValidation(String password) {
        if (password.length() <=7) {
            throw new InvalidPasswordException("password can not be less than 8 characters");
        }

    }
    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        User foundEmail = userRepository.findUserByEmail(loginRequest.getEmail());
        User foundPassword = userRepository.findUserByPassword(loginRequest.getPassword());
        if(!userEmailExists(loginRequest.getEmail())) throw  new InvalidDetailsException("invalid user details ");
        if (foundPassword != null && ! foundPassword.getPassword().equals(loginRequest.getPassword()))
         throw  new InvalidDetailsException("invalid password");
    foundEmail.setEnabled(true);
    userRepository.save(foundEmail);
        loginResponse.setMessage("login successful");
        return loginResponse;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UpdateResponse updateUser(UpdateRequest updateRequest) {
        return null;
    }


}
