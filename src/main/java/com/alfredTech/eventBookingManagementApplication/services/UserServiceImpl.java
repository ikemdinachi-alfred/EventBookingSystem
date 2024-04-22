package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.data.repositories.UserRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.UpdateRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.LoginResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.RegistrationResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.UpdateResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
        userEmailIsValid(registrationRequest.getEmail());
        Optional<User> foundUser = userExist(registrationRequest.getEmail());
        if (foundUser.isPresent()) { throw  new UserExistException
                (" user with email "+registrationRequest.getEmail()+" already exists"); }

        user.setEnabled(false);
        userRepository.save(user);
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setMessage("registration successful");
        return registrationResponse;
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
        Optional<User> foundEmail = userExist(loginRequest.getEmail());
        User foundUser = foundEmail.orElseThrow(() -> new InvalidDetailsException("invalid email"));
       if (!foundUser.getPassword().equals(loginRequest.getPassword())) {
           throw new InvalidDetailsException("invalid details");
       }
       foundUser.setEnabled(true);
       userRepository.save(foundUser);
       loginResponse.setMessage("login successful");
        return loginResponse;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
public UpdateResponse updateUser(String oldEmail,UpdateRequest updateRequest) {
        UpdateResponse updateResponse = new UpdateResponse();
        oldEmail = updateRequest.getOldEmail();
        Optional<User> foundUser = userExist(oldEmail);
        User existingUser = foundUser.orElseThrow(() -> new InvalidDetailsException("check your old email"));
        existingUser.setFirstName(updateRequest.getFirstName());
        existingUser.setLastName(updateRequest.getLastName());
        existingUser.setEmail(updateRequest.getCurrentEmail());
        existingUser.setPassword(updateRequest.getNewPassword());
        userRepository.save(existingUser);
        updateResponse.setMessage("update successful");
        return updateResponse;
    }

    @Override
    public Optional<User> userExist(String email) {
        return userRepository.findUserByEmail(email);
    }


}
