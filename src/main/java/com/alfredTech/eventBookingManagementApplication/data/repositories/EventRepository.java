package com.alfredTech.eventBookingManagementApplication.data.repositories;

import com.alfredTech.eventBookingManagementApplication.data.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
     Event findEventByName(String eventName);
}
