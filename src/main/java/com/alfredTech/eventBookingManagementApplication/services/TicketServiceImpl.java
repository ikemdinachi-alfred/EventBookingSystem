package com.alfredTech.eventBookingManagementApplication.services;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.Ticket;
import com.alfredTech.eventBookingManagementApplication.data.repositories.TicketRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.TicketBookingRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.TicketBookingResponse;
import com.alfredTech.eventBookingManagementApplication.exceptions.SpaceFullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository;
    private final AtomicLong ticketCounter = new AtomicLong(1);
    private static final Long MAX_TICKETS = 1000L;
    @Override
    public TicketBookingResponse createTicket(TicketBookingRequest request) {
        Event foundEvent = ticketRepository.findEventByName(request.getEventName());
        Ticket ticket = new Ticket();
        ticket.setEmail(request.getEmail());
        ticket.setTicketNo(assignTicketNumber());
        ticket.setEvent(foundEvent);
        ticketRepository.save(ticket);
        TicketBookingResponse response = new TicketBookingResponse();
        response.setMessage("Ticket created successfully......");
        return response;
    }




        public Long assignTicketNumber() {
            Long ticketNumber = ticketCounter.incrementAndGet();
            if (ticketNumber > MAX_TICKETS) {
                throw new SpaceFullException("No more tickets available.");
            }
            return ticketNumber;
        }
    }


