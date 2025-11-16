package model.tickets;

import model.products.ProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TicketService {
    private static final DateTimeFormatter opening = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm-");
    private HashMap<String, Ticket> tickets = new HashMap<>();
    private final int MAX_TICKETS = 100;
    private int numTickets = 0;

    public Ticket ticketNew() {
        String openingTimestamp = LocalDateTime.now().format(opening);
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999+1);
        String id = openingTimestamp + randomNumber;
        Ticket ticket = new Ticket(id);
        tickets.put(id, ticket);
        numTickets++;
        return ticket;
    }

    public Ticket ticketNew(String ticketID) {
        Ticket ticket = new Ticket(ticketID);
        tickets.put(ticketID, ticket);
        numTickets++;
        return ticket;
    }

    public void addProductToTicket(String ticketID, String cashID, int id, int amount, ProductService productService) {
        if (tickets.containsKey(ticketID)) {
            Ticket ticket = tickets.get(ticketID);
            ticket.addProductToTicket(id, amount, productService);
        } else {

        }
    }

    public Ticket getTicket(String id) {
        return tickets.get(id);
    }

    public void removeTicket(String id) {
        tickets.remove(id);
    }

    public void removeProductFromTicket(String ticketID, int id) {

    }

    public Map<String, Ticket> getTickets() {
        return tickets;
    }

}
