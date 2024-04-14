package com.alfredTech.eventBookingManagementApplication.dtos.request;
import com.alfredTech.eventBookingManagementApplication.data.models.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
public class CreateEventRequest {
    private String eventName;
    private String description;
    private LocalDate date;
    private Integer attendees;
    private Category categories;

}
