package com.alfredTech.eventBookingManagementApplication;
import com.alfredTech.eventBookingManagementApplication.data.repositories.UserRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.UpdateRequest;
import com.alfredTech.eventBookingManagementApplication.exceptions.*;
import com.alfredTech.eventBookingManagementApplication.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void doThisFirst() {
        userRepository.deleteAll();
    }

   @Test
   public void test_that_when_user_register_total_user_is_one() {
       RegistrationRequest request = new RegistrationRequest();
       request.setFirstName("John");
       request.setLastName("Doe");
       request.setEmail("john@doe.com");
       request.setPassword("password");
       userService.registerUser(request);
       assertEquals(1,userRepository.count());

    }
    @Test
    public void test_that_when_user_register_with_password_less_than_8_characters_an_exception_is_thrown() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe.com");
        request.setPassword("admin");
        assertThrows(InvalidPasswordException.class,()->userService.registerUser(request));

    }
    @Test
    public void test_that_when_two_user_register_total_user_is_two() {
        RegistrationRequest request = new RegistrationRequest();
        RegistrationRequest request2 = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe.com");
        request.setPassword("password1");
        userService.registerUser(request);
        assertEquals(1,userRepository.count());
        request2.setFirstName("bull");
        request2.setLastName("gorge");
        request2.setEmail("bull@gorge.com");
        request2.setPassword("password2");
        userService.registerUser(request2);
        assertEquals(2,userRepository.count());

    }
    @Test
    public void test_that_when_registered_user_try_to_register_again_with_same_mail_an_exception_is_thrown() {
        RegistrationRequest request = new RegistrationRequest();
        RegistrationRequest request2 = new RegistrationRequest();
       request.setFirstName("Johnny");
       request.setLastName("Doe");
       request.setEmail("johnny@doe.com");
       request.setPassword("password");
       userService.registerUser(request);
       assertEquals(1,userRepository.count());
       request2.setFirstName("Johnny");
       request2.setLastName("Bush");
       request2.setEmail("johnny@doe.com");
       request2.setPassword("password2");
       assertThrows(UserExistException.class,()->userService.registerUser(request2));
       assertEquals(1,userRepository.count());
    }
    @Test
    public void test_that_when_user_register_with_incorrect_email_format_missing_At_an_exception_is_thrown_by_db(){
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("funny");
        request.setLastName("Bros");
        request.setEmail("funnyBros.com");
        request.setPassword("password");
       assertThrows(NotAValidEmailException.class,()->userService.registerUser(request));
    }
    @Test
    public void test_that_when_user_register_with_incorrect_email_format_missing_dot_an_exception_is_thrown_by_db(){
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("funny");
        request.setLastName("Bros");
        request.setEmail("funnyBros@gmailcom");
        request.setPassword("password");
        assertThrows(NotAValidEmailException.class,()->userService.registerUser(request));
    }
    @Test
    public void test_that_when_user_register_user_can_login_with_same_email_and_password() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe.com");
        request.setPassword("password");
        userService.registerUser(request);
        assertEquals(1,userRepository.count());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe.com");
        loginRequest.setPassword("password");
        userService.loginUser(loginRequest);
        assertEquals(1,userRepository.count());

    }
    @Test
    public void test_that_when_registered_user_try_to_login_with_wrong_email_an_exception_is_thrown() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe.com");
        request.setPassword("password");
        userService.registerUser(request);
        assertEquals(1, userRepository.count());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doew.com");
        loginRequest.setPassword("password");
        assertThrows(InvalidDetailsException.class, () -> userService.loginUser(loginRequest));
    }

    @Test
    public void test_that_when_a_registered_user_try_to_login_with_wrong_password_an_exception_is_thrown() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe.com");
        request.setPassword("password");
        userService.registerUser(request);
        assertEquals(1, userRepository.count());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe.com");
        loginRequest.setPassword("password12 ");
        assertThrows(InvalidDetailsException.class, () -> userService.loginUser(loginRequest));
    }

    @Test
    public void test_that_registered_users_can_update_information(){
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe.com");
        request.setPassword("password1");
        userService.registerUser(request);
        assertEquals(1,userRepository.count());
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setFirstName("Johnny");
        updateRequest.setLastName("Bush");
        updateRequest.setOldEmail("john@doe.com");
        updateRequest.setCurrentEmail("johnny@doe.com");
        updateRequest.setNewPassword("password2");
        userService.updateUser(updateRequest.getOldEmail(),updateRequest);
        assertEquals(1,userRepository.count());

    }



}
