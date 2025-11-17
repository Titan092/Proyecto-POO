package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.tickets.Ticket;
import model.tickets.TicketService;
import model.tickets.TicketStatus;

import java.util.HashMap;

public class Cash extends User {
    public Cash(String id, String name, String email){
        super(id,name,email);
    }

}
