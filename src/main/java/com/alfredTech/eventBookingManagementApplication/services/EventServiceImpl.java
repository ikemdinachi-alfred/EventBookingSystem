package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.data.repositories.EventRepository;
import com.alfredTech.eventBookingManagementApplication.data.repositories.UserRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.AttendeesExceededException;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidDescriptionException;
import com.alfredTech.eventBookingManagementApplication.exceptions.LogInException;
import com.alfredTech.eventBookingManagementApplication.exceptions.UserNotFoundException;
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
    public CreateEventResponse createAnEvent(String email, CreateEventRequest createEventRequest) {
        email = createEventRequest.getEmail();
        User foundEmail = userRepository.findUserByEmail(email);
        if (!userService.userEmailExists(email))throw  new UserNotFoundException("User not found with email: " + email);
        if (!foundEmail.isEnabled()) throw new LogInException("you must login to access this service ");
        Event event = new Event();
        event.setEventName(createEventRequest.getEventName());
        event.setEventDescription(createEventRequest.getDescription());
        validateDescription(createEventRequest.getDescription());
        event.setDate(createEventRequest.getDate());
        event.setAttendees(createEventRequest.getAttendees());
        validateEventAttendees(createEventRequest.getAttendees());
        event.setCategories(createEventRequest.getCategories());
        event.setUser(foundEmail);
        eventRepository.save(event);
        CreateEventResponse response = new CreateEventResponse();
        response.setMessage("Event created successfully \n check details \n" + event);
        return response;
    }

    @Override
    public Iterable<Event> getAllEventsBelongingTo(String email) {
        User foundEmail = userRepository.findUserByEmail(email);
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
