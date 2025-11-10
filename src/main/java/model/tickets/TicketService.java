package model.tickets;

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

    public void add(String userID) {
        String openingTimestamp = LocalDateTime.now().format(opening);
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999+1);
        String id = openingTimestamp + randomNumber;
        Ticket ticket = new Ticket(id, userID);
        tickets.put(id, ticket);
        numTickets++;
    }

    public Ticket getTicket(String id) {
        return tickets.get(id);
    }

    public void delete(String id) {
        tickets.remove(id);
    }

    public Map<String, Ticket> getTickets() {
        return tickets;
    }

}
