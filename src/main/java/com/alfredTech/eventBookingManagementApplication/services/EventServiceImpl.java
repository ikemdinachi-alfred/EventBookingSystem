package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.data.repositories.EventRepository;
import com.alfredTech.eventBookingManagementApplication.data.repositories.UserRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.ViewAllEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;


    @Override
    public CreateEventResponse createAnEvent(CreateEventRequest createEventRequest) {
        User foundEmail = userRepository.findUserByEmail(createEventRequest.getEmail());
        if (!userService.userEmailExists(createEventRequest.getEmail()))throw  new UserNotFoundException("User not found with email: " + createEventRequest.getEmail());
        if (!foundEmail.isEnabled()) throw new LogInException("you must login to access this service ");
        Event event = new Event();
        createEvent(createEventRequest, event);
        event.setUser(foundEmail);
        eventRepository.save(event);
        CreateEventResponse response = new CreateEventResponse();
        response.setMessage("Event created successfully..... ");
        return response;
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
    public Iterable<Event> getAllEventsBelongingTo(ViewAllEventRequest request) {
        User foundEmail = userRepository.findUserByEmail(request.getEmail());
        return foundEmail.getEvents();
    }

    private void validateDescription(String eventDescription){
        if (eventDescription.length()>200)
            throw new InvalidDescriptionException("Description must be less that 500 characters");
    }
    private void validateEventAttendees(Long attendees){
        if (attendees > 1000) throw new AttendeesExceededException("Attendees must be less that 1000 ");
    }


}
