package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.ViewAllEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;

import java.util.Set;

public interface EventService {
    CreateEventResponse createAnEvent(CreateEventRequest createEventRequest);
    Set<Event> getAllEventsBelongingTo(ViewAllEventRequest request);
    Event findEventByName(String name);
}
