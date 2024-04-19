package com.alfredTech.eventBookingManagementApplication;
import com.alfredTech.eventBookingManagementApplication.data.models.Category;
import com.alfredTech.eventBookingManagementApplication.data.repositories.EventRepository;
import com.alfredTech.eventBookingManagementApplication.data.repositories.TicketRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.TicketBookingRequest;
import com.alfredTech.eventBookingManagementApplication.services.EventService;
import com.alfredTech.eventBookingManagementApplication.services.TicketService;
import com.alfredTech.eventBookingManagementApplication.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TicketServiceTest {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketService ticketService;

    @Test
    public void Test_that_when_Users_buy_tickets_for_an_existing_even_total_ticket_equals_one(){
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe23.com");
        request.setPassword("password1");
        userService.registerUser(request);
        //user login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe23.com");
        loginRequest.setPassword("password1");
        userService.loginUser(loginRequest);
        //user create event
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setEmail("john@doe23.com");
        createEventRequest.setEventName("Test Event");
        createEventRequest.setDescription("Test Description");
        createEventRequest.setAttendees(900L);
        createEventRequest.setCategories(Category.CONCERT);
        createEventRequest.setCreatedDate(createEventRequest.getCreatedDate());
        eventService.createAnEvent(createEventRequest);
        assertEquals(1, eventRepository.count());

        TicketBookingRequest request1 = new TicketBookingRequest();
        request1.setName("james OldMan");
        request1.setEmail("james@oldman.com");
        request1.setEventName("Test Event");
        ticketService.createTicket(request1);
        assertEquals(1, ticketRepository.count());



    }

}
