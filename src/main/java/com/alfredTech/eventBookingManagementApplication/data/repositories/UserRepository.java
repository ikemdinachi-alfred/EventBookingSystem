package com.alfredTech.eventBookingManagementApplication.data.repositories;

import com.alfredTech.eventBookingManagementApplication.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User>  findUserByEmail(String email);
}
