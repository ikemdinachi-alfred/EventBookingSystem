package com.alfredTech.eventBookingManagementApplication.data.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 100)
    private String firstName;
    @Size(min = 1, max = 100)
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();
    private boolean isEnabled;

}
