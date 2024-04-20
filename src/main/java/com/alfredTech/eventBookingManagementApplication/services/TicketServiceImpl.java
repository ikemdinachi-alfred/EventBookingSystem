package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.Ticket;
import com.alfredTech.eventBookingManagementApplication.data.repositories.TicketRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.TicketBookingRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.TicketBookingResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.EventNotFoundException;
import com.alfredTech.eventBookingManagementApplication.exceptions.SpaceFullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository  ticketRepository;
    @Autowired
    private EventService eventService;
    private static  Long MIN_TICKETS = 1L;
    @Override
    public TicketBookingResponse createTicket(TicketBookingRequest request) {
        Optional<Event>eventExist = eventService.findEventByName(request.getEventName());
        Event foundEvent = eventExist.orElseThrow(() -> new EventNotFoundException("Event not found"));
        Ticket ticket = new Ticket();
        ticket.setEmail(request.getEmail());
        ticket.setTicketNo(assignTicketNumber(request.getEventName()));
        ticket.setEvent(foundEvent);
        ticketRepository.save(ticket);
        TicketBookingResponse response = new TicketBookingResponse();
        response.setMessage("Ticket created successfully......");
        return response;
    }




        public Long assignTicketNumber(String eventName) {
            Optional<Event>eventExist = eventService.findEventByName(eventName);
            Event foundEvent = eventExist.orElseThrow(() -> new EventNotFoundException("Event not found"));
            if (MIN_TICKETS<=foundEvent.getAttendees()) MIN_TICKETS+=1;
            else throw new SpaceFullException("No more tickets available.");
            return MIN_TICKETS;
        }
    }


