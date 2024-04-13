package com.alfredTech.eventBookingManagementApplication.data.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    @CreatedDate
    private LocalDate date;
    @Size(min = 1, max = 1000)
    private Integer attendees;
    @Size(min = 1, max = 500)
    private String eventDescription;
    @Enumerated(EnumType.STRING)
    private Category categories;
    @OneToMany
    private List<Ticket> tickets;
}
/*
- name (limited to 100 characters);
- date (in a valid date format);
- available attendees count (positive integer limited to 1000);
- event description (limited to 500 characters).
- category (Concert, Conference, Game)
 */