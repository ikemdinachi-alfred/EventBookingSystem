package com.alfredTech.eventBookingManagementApplication.data.repositories;
import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
     Optional<Event> findEventByEventName(String name);
     List<Event> findEventsByUserEmail(String email);

}
