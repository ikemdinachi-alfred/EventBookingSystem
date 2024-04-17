package com.alfredTech.eventBookingManagementApplication.services;

import com.alfredTech.eventBookingManagementApplication.dtos.request.TicketBookingRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.response.TicketBookingResponse;

public interface TicketService {
    TicketBookingResponse createTicket(TicketBookingRequest request);
}
