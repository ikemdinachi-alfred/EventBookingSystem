package com.alfredTech.eventBookingManagementApplication.controller;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidDescriptionException;
import com.alfredTech.eventBookingManagementApplication.exceptions.LogInException;
import com.alfredTech.eventBookingManagementApplication.exceptions.SpaceFullException;
import com.alfredTech.eventBookingManagementApplication.exceptions.UserNotFoundException;
import com.alfredTech.eventBookingManagementApplication.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/event")

public class EventController {
    @Autowired
     private EventService eventService;


    @PostMapping("/create")
    public ResponseEntity<CreateEventResponse> createEvent(@RequestBody CreateEventRequest createEventRequest) {
        try {
            CreateEventResponse response = eventService.createAnEvent(createEventRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidDescriptionException | SpaceFullException | UserNotFoundException |
                 LogInException exception) {
            CreateEventResponse errorResponse = new CreateEventResponse();
            errorResponse.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/view_all")
    public List<Event> viewAllEvents() {
        return eventService.getAllCreatedEvent();
    }


}
