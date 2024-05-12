package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.User;
import com.alfredTech.eventBookingManagementApplication.data.repositories.EventRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.CreateEventResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserServiceImpl userService;




    @Override
    public CreateEventResponse createAnEvent(CreateEventRequest createEventRequest) {
        Optional<User> foundEmail = userService.userExist(createEventRequest.getEmail());
        User foundUser = foundEmail.orElseThrow(() -> new InvalidDetailsException("invalid user details "));
        if (!foundUser.isEnabled()) throw new LogInException("you must login to access this service ");
        Event event = new Event();
        Optional<Event> eventExists = eventRepository.findEventByEventName(createEventRequest.getEventName());
        if (eventExists.isPresent()) throw new EventExistException("event already exists ");
        createEvent(createEventRequest, event);
       event.setUser(foundUser);
        eventRepository.save(event);
        CreateEventResponse response = new CreateEventResponse();
        response.setMessage("Event created successfully..... ");
        return response;
    }

    @Override
    public List<Event> findAllEventBelongingTo(String email) {
        return eventRepository.findEventsByUserEmail(email);
    }


    @Override
    public Event findEventByName(String name) {
        return eventRepository.findEventByEventName(name)
                .orElseThrow(()-> new EventNotFoundException("Event does not exist"));
    }

    @Override
    public List<Event> getAllCreatedEvent() {
        return eventRepository.findAll();
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



    private void validateDescription(String eventDescription){
        if (eventDescription.length()>200)
            throw new InvalidDescriptionException("Description must be less that 500 characters");
    }
    private void validateEventAttendees(Long attendees){
        if (attendees > 1000) throw new SpaceFullException("Attendees must be less that 1000 ");
    }







}
