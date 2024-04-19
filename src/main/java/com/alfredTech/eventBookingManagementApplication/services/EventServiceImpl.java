package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.data.repositories.EventRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.ViewAllEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserServiceImpl userService;



    @Override
    public CreateEventResponse createAnEvent(CreateEventRequest createEventRequest) {
        User foundEmail = userService.userEmailExists(createEventRequest.getEmail());
        if (userService.userEmailExists(createEventRequest.
                getEmail()).getEmail()
                .isEmpty())throw  new UserNotFoundException("User not found with email: " + createEventRequest.getEmail());
        if (!foundEmail.isEnabled()) throw new LogInException("you must login to access this service ");
        Event event = new Event();
        createEvent(createEventRequest, event);
        event.setUser(foundEmail);
        eventRepository.save(event);
        CreateEventResponse response = new CreateEventResponse();
        response.setMessage("Event created successfully..... \n"+print(createEventRequest));
        return response;
    }
    public String print( CreateEventRequest request){
        return String.format("""
                Event Name: %s
                Event Description: %s
                Number of Attendees: %d
                Category: %s
                Event Created Date: %s
                """,request.getEventName(),
                request.getDescription(),
                request.getAttendees(),
                request.getCategories(),
                request.getCreatedDate());

    }

    private void createEvent(CreateEventRequest createEventRequest, Event event) {
        event.setEventName(createEventRequest.getEventName());
        event.setEventDescription(createEventRequest.getDescription());
        validateDescription(createEventRequest.getDescription());
        event.setCreatedDate(createEventRequest.getCreatedDate());
        event.setAttendees(createEventRequest.getAttendees());
        validateEventAttendees(createEventRequest.getAttendees());
        event.setCategories(createEventRequest.getCategories());
    }

    @Override
    public Set<Event> getAllEventsBelongingTo(ViewAllEventRequest request) {
        User foundEmail = userService.userEmailExists(request.getEmail());
        return foundEmail.getEvents();
    }

    @Override
    public Event findEventByName(String name) {
        return eventRepository.findByEventName(name);
    }

    private void validateDescription(String eventDescription){
        if (eventDescription.length()>200)
            throw new InvalidDescriptionException("Description must be less that 500 characters");
    }
    private void validateEventAttendees(Long attendees){
        if (attendees > 1000) throw new SpaceFullException("Attendees must be less that 1000 ");
    }



}
