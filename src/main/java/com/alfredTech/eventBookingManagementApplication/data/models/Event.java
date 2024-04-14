package com.alfredTech.eventBookingManagementApplication.data.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private String date;
    private Long attendees;
    private String eventDescription;
    @Enumerated(EnumType.STRING)
    private Category categories;
    @OneToMany
    private List<Ticket> tickets;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
