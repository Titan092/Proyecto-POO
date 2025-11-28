package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.products.ProductService;
import model.tickets.Ticket;
import model.tickets.TicketStatus;

import java.util.HashMap;

/**
 * Abstract class representing a user.
 * It implements the IUser interface.
 */
public abstract class User implements IUser {

    private String id;
    private String name;
    private String email;
    protected HashMap<String, Ticket> tickets = new HashMap<>();

    /**
     * Constructor for User.
     * @param id User ID.
     * @param name User name.
     * @param email User email.
     */
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

    /**
     * Create a new ticket for the user.
     * @param ticketID Ticket ID.
     * @param ticket Ticket object.
     */
    public void newTicket(String ticketID, Ticket ticket) {
        if (tickets.containsKey(ticketID))  {
            System.out.println(ErrorMessageHandler.getTicketAlreadyExists() );
        }
        tickets.put(ticketID, ticket);
    }

    /**
     * Add a product to an existing ticket.
     * It could be done in cashier besides user.
     * @param ticketID Ticket ID.
     * @param productID Product ID.
     * @param amount Amount of product.
     * @param productService Product service for product management.
     * @return Message indicating the result of the operation.
     */
    public String addProductToTicket(String ticketID, int productID, int amount, ProductService productService) {
        String message;
        Ticket existingTicket = tickets.get(ticketID);
        if (existingTicket != null && existingTicket.getStatus() == TicketStatus.CLOSED) {
            message = ErrorMessageHandler.getUSE_CLOSED_TICKET();
        } else if (existingTicket != null) {
            message = existingTicket.addProductToTicket(productID, amount, productService);
        } else {
            message = ErrorMessageHandler.getTicketDoesntExist();
        }
        return message;
    }

    /**
     * Add a personalizable product to an existing ticket.
     * It could be done in cashier besides user.
     * @param ticketID Ticket ID.
     * @param productID Product ID.
     * @param amount Amount of product.
     * @param personalizableTexts Array of personalizable texts.
     * @param productService Product service for product management.
     * @return Message indicating the result of the operation.
     */
    public String addProductToTicket(String ticketID, int productID, int amount, String[] personalizableTexts, ProductService productService) {
        String message;
        Ticket existingTicket = tickets.get(ticketID);
        if (existingTicket != null && existingTicket.getStatus() == TicketStatus.CLOSED) {
            message = ErrorMessageHandler.getUSE_CLOSED_TICKET();
        } else if (existingTicket != null) {
            message = existingTicket.addProductToTicket(productID, amount, personalizableTexts, productService);
        } else {
            message = ErrorMessageHandler.getTicketDoesntExist();
        }
        return message;
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
