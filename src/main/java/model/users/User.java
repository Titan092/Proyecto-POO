package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.products.ProductService;
import model.tickets.Ticket;
import model.tickets.TicketStatus;

import java.util.HashMap;

public abstract class User implements IUser {

    private String id;
    private String name;
    private String email;
    protected HashMap<String, Ticket> tickets = new HashMap<>();

    public User(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void newTicket(String ticketID, Ticket ticket) {
        if (tickets.containsKey(ticketID))  {
            System.out.println(ErrorMessageHandler.getTicketAlreadyExists() );
        }
        tickets.put(ticketID, ticket);
    }

    public void addProductToTicket(String ticketID, int productID, int amount, ProductService productService) {
        Ticket existingTicket = tickets.get(ticketID);
        if (existingTicket != null && existingTicket.getStatus() == TicketStatus.CLOSED) {
            System.out.println(ErrorMessageHandler.getUSE_CLOSED_TICKET() );
        } else if (existingTicket != null) {
            existingTicket.addProductToTicket(productID, amount, productService);
        } else {
            System.out.println(ErrorMessageHandler.getTicketDoesntExist());
        }
    }

    public Ticket getTicket(String ticketID) {
        return tickets.get(ticketID);
    }

    public void removeTicket(String ticketID) {
        tickets.remove(ticketID);
    }

    public HashMap<String, Ticket> getTickets() {
        return tickets;
    }
}
