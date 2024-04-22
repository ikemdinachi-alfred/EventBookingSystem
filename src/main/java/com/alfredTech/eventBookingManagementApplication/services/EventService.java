package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.ViewAllEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventService {
    CreateEventResponse createAnEvent(CreateEventRequest createEventRequest);
    List<Event> getAllEventsBelongingTo(ViewAllEventRequest request);
    Optional<Event> findEventByName(String name);
}
