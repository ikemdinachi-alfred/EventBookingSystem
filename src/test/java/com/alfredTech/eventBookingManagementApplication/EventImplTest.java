package com.alfredTech.eventBookingManagementApplication;
import com.alfredTech.eventBookingManagementApplication.data.models.Category;
import com.alfredTech.eventBookingManagementApplication.data.repositories.EventRepository;
import com.alfredTech.eventBookingManagementApplication.data.repositories.UserRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.CreateEventRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.LoginRequest;
import com.alfredTech.eventBookingManagementApplication.dtos.request.RegistrationRequest;
import com.alfredTech.eventBookingManagementApplication.exceptions.AttendeesExceededException;
import com.alfredTech.eventBookingManagementApplication.exceptions.InvalidDescriptionException;
import com.alfredTech.eventBookingManagementApplication.services.EventServiceImpl;
import com.alfredTech.eventBookingManagementApplication.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EventImplTest {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    EventServiceImpl eventService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
   public void setUp() {
       eventRepository.deleteAll();
       userRepository.deleteAll();
   }
   @Test
    public void test_That_users_can_Create_an_Event() {
       RegistrationRequest request = new RegistrationRequest();
       request.setFirstName("John");
       request.setLastName("Doe");
       request.setEmail("john@doe23.com");
       request.setPassword("password1");
       userService.registerUser(request);
       //user login
       LoginRequest loginRequest = new LoginRequest();
       loginRequest.setEmail("john@doe23.com");
       loginRequest.setPassword("password1");
       userService.loginUser(loginRequest);
       //user create event
       CreateEventRequest createEventRequest = new CreateEventRequest();
       createEventRequest.setEmail("john@doe23.com");
       createEventRequest.setEventName("Test Event");
       createEventRequest.setDescription("Test Description");
       createEventRequest.setAttendees(900L);
       createEventRequest.setCategories(Category.CONCERT);
       createEventRequest.setCreatedDate(createEventRequest.getCreatedDate());
       eventService.createAnEvent(createEventRequest);
       assertEquals(1, eventRepository.count());
    }
    @Test
    public void test_That_when_users__Create_Event_with_more_than_1000_Attendees_an_exception_should_be_thrown() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe23.com");
        request.setPassword("password1");
        userService.registerUser(request);
        //user login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe23.com");
        loginRequest.setPassword("password1");
        userService.loginUser(loginRequest);
        //user create event
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setEmail("john@doe23.com");
        createEventRequest.setEventName("Test Event");
        createEventRequest.setDescription("Test Description");
        createEventRequest.setAttendees(1001L);
        createEventRequest.setCategories(Category.CONCERT);
        createEventRequest.setCreatedDate(createEventRequest.getCreatedDate());
        assertThrows(AttendeesExceededException.class,()->eventService.createAnEvent(createEventRequest));

    }
    @Test
    public void test_That_when_users__Create_Event_with_more_than_500_Character_description_an_exception_should_be_thrown() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@doe23.com");
        request.setPassword("password1");
        userService.registerUser(request);
        //user login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe23.com");
        loginRequest.setPassword("password1");
        userService.loginUser(loginRequest);
        //user create event
        String description = "Test Description as far as the test does not fail is correct" +
                "th;nvrijkdnionnsadnionrvnoienrionnqeinvieroninivoqneionivqienqeninioveqiniiio" +
                " nfinionoivnioinj jsd j j jf jkd jk j jdj sdj ninsddb hj h h xh hs" +
                "kj j jh jh j jhsd hx  dhd hjhj dh hsddd" +
                "sdj";
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setEmail("john@doe23.com");
        createEventRequest.setEventName("Test Event");
        createEventRequest.setDescription(description);
        createEventRequest.setAttendees(750L);
        createEventRequest.setCategories(Category.CONCERT);
        createEventRequest.setCreatedDate(createEventRequest.getCreatedDate());
        assertThrows(InvalidDescriptionException.class,()->eventService.createAnEvent(createEventRequest));

    }


}
