package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;

public interface EventService {
    CreateEventResponse createAnEvent(String email, CreateEventRequest createEventRequest);
    Iterable<Event> getAllEventsBelongingTo(String email);
}
