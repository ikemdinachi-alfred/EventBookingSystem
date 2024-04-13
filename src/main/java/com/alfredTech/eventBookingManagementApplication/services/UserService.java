package com.alfredTech.eventBookingManagementApplication.services;

import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.UpdateRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.LoginResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.RegistrationResponse;
import com.alfredTech.eventBookingManagementApplication.dtos.response.UpdateResponse;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    Iterable<User> getAllUsers();
    UpdateResponse updateUser(UpdateRequest updateRequest);
}
