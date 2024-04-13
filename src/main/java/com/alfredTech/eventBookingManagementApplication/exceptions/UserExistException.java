package com.alfredTech.eventBookingManagementApplication.exceptions;

public class UserExistException extends EventBookingException{
    public UserExistException(String message) {
        super(message);
    }
}
