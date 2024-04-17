package com.alfredTech.eventBookingManagementApplication;
import com.alfredTech.eventBookingManagementApplication.data.repositories.TicketRepository;
import com.alfredTech.eventBookingManagementApplication.dtos.request.TicketBookingRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketServiceTest {
    @Autowired
    TicketRepository ticketRepository;

    @Test
    public void Test_that_when_Users_buy_tickets_for_an_existing_even_total_ticket_equals_one(){
        TicketBookingRequest request = new TicketBookingRequest();
        request.setName("james OldMan");
        request.setEmail("james@oldman.com");
        request.setEventName("");


    }

}
