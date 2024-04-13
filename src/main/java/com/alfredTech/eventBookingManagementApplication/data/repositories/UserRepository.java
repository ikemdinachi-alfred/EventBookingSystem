package com.alfredTech.eventBookingManagementApplication.data.repositories;

import com.alfredTech.eventBookingManagementApplication.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByPassword(String password);
}
