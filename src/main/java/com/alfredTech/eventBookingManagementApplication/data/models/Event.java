package com.alfredTech.eventBookingManagementApplication.data.models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDateTime createdDate;
    private Long attendees;
    private String eventDescription;
    @Enumerated(EnumType.STRING)
    private Category categories;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
