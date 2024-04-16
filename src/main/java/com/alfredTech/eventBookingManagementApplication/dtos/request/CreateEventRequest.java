package com.alfredTech.eventBookingManagementApplication.dtos.request;
import com.alfredTech.eventBookingManagementApplication.data.models.Category;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CreateEventRequest {
    private String email;
    private String eventName;
    private String description;
    private LocalDateTime createdDate = LocalDateTime.now();
    private Long attendees;
    private Category categories;



}
