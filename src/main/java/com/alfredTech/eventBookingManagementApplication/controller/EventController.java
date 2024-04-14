package com.alfredTech.eventBookingManagementApplication.controller;

import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.AttendeesExceededException;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidDescriptionException;
import com.alfredTech.eventBookingManagementApplication.exceptions.UserNotFoundException;
import com.alfredTech.eventBookingManagementApplication.services.EventServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@JsonFormat(pattern = "yyyy-MM-dd")
@RequestMapping("/api/event")
public class EventController {
    private LocalDate date;

    @Autowired
    private EventServiceImpl eventService;

    @PostMapping("/create")
    public CreateEventResponse createEvent(@RequestParam String email, @RequestBody CreateEventRequest createEventRequest) {
        try {

            return eventService.createAnEvent(email,createEventRequest);
        }catch (InvalidDescriptionException | AttendeesExceededException | UserNotFoundException exception){
            CreateEventResponse createEventResponse = new CreateEventResponse();
            createEventResponse.setMessage(exception.getMessage());
            return createEventResponse;

        }
    }
    @GetMapping("/view_all")
    public Iterable<Event> getAllEvents( @RequestParam String email) {
        return eventService.getAllEventsBelongingTo(email);
    }

}
