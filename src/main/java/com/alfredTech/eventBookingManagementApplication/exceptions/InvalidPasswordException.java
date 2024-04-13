package com.alfredTech.eventBookingManagementApplication.exceptions;

import org.hibernate.exception.ConstraintViolationException;

public class InvalidPasswordException extends EventBookingException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
