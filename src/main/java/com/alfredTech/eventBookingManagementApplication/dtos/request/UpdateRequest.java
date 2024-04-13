package com.alfredTech.eventBookingManagementApplication.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateRequest {
    private String oldEmail;
    private String newEmail;
    private String oldPassword;
    private String newPassword;
}
