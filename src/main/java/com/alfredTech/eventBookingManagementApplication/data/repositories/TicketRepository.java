package com.alfredTech.eventBookingManagementApplication.data.repositories;

import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import com.alfredTech.eventBookingManagementApplication.data.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Event findEventByName(String eventName);
}
