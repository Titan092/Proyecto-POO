package model.tickets;

import exceptionHandler.ErrorMessageHandler;
import model.products.CustomProduct;
import model.products.IProduct;
import model.products.ProductService;
import model.users.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TicketService {
    private static final DateTimeFormatter opening = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm-");
    private static final DateTimeFormatter closing = DateTimeFormatter.ofPattern("-yy-MM-dd-HH:mm");

    /**
     * Generates a new ticket ID and creates a ticket for the given Cashier and Client.
     *
     * @param cashID      The ID of the Cashier.
     * @param clientID    The ID of the Client.
     * @param userService The service to retrieve user data.
     * @return A status string describing the result or errors.
     */
    public String ticketNew(String cashID, String clientID, UserService userService) {
        int numRandom = ThreadLocalRandom.current().nextInt(10000, 99999+1);
        String ticketID = LocalDateTime.now().format(opening) + numRandom;

        return ticketNew(ticketID, cashID, clientID, userService);
    }

    /**
     * Creates a ticket with a specific Ticket ID for the given Cashier and Client.
     *
     * @param ticketID    The unique identifier for the ticket.
     * @param cashID      The ID of the Cashier.
     * @param clientID    The ID of the Client.
     * @param userService The service to retrieve user data.
     * @return A status string describing the result or errors.
     */
    public String ticketNew(String ticketID, String cashID, String clientID, UserService userService) {
        if (clientID.length() != 9 || !Character.isAlphabetic(clientID.charAt(8))) {
            return "The clientID is not valid";
        }
        if (cashID.length() != 9 || cashID.charAt(0) != 'U') {
            return "The cashID is not valid";
        }
        Map<String, IUser> casherLists = userService.getUsers();
        Cash casher = (Cash) casherLists.get(cashID);
        Client client = (Client) casherLists.get(clientID);

        Ticket ticket = new Ticket(ticketID);
        casher.newTicket(ticketID, ticket);
        client.newTicket(ticketID, ticket);

        return "Ticket : " + ticketID + "\n" +
                "  " + "Total price: 0.0\n" +
                "  " + "Total discount: 0.0\n" +
                "  " + "Final Price: 0.0\n" +
                "ticket new: ok\n";
    }


    /**
     * Adds a standard product to a ticket.
     *
     * @param ticketID       The unique identifier of the ticket.
     * @param cashID         The unique identifier of the cashier.
     * @param productID      The unique identifier of the product.
     * @param amount         The quantity of the product to add.
     * @param userService    Service to retrieve user data.
     * @param productService Service to retrieve product data.
     * @return A formatted string with ticket details if successful, or an error message otherwise.
     */
    public String ticketAdd(String ticketID, String cashID, int productID, int amount, UserService userService, ProductService productService) {
        if (cashID.length() != 9 && cashID.charAt(0) != 'U') {
            return "The cashID is not valid";
        }

        Map<String, IUser> users = userService.getUsers();
        if (!users.containsKey(cashID)) {
            return "No cashier found";
        }
        Cash cashier = (Cash) users.get(cashID);
        if (!cashier.getTickets().containsKey(ticketID)) {
            return "This worker did not create the ticket with this id";
        }

        // print means a successful add, otherwise it's an error message.
        String result = cashier.addProductToTicket(ticketID, productID, amount, productService);
        if ("print".equals(result)) {
            Ticket ticket = cashier.getTicket(ticketID);
            return "Ticket : " + ticketID + "\n" +
                    ticket.printTicket() +
                    "ticket add: ok\n";
        } else {
            return result;
        }
    }

    /**
     * Adds a customizable product (with personalized text) to a ticket.
     *
     * @param ticketID            The unique identifier of the ticket.
     * @param cashID              The unique identifier of the cashier.
     * @param productID           The unique identifier of the product.
     * @param amount              The quantity of the product to add.
     * @param personalizableTexts Array of strings for product customization.
     * @param userService         Service to retrieve user data.
     * @param productService      Service to retrieve product data.
     * @return A formatted string with ticket details if successful, or an error message.
     */
    public String ticketAdd(String ticketID, String cashID, int productID, int amount, String[] personalizableTexts, UserService userService, ProductService productService) {
        if (cashID.length() != 9 && cashID.charAt(0) != 'U') {
            return "The cashID is not valid";
        }

        Map<String, IUser> users = userService.getUsers();
        if (!users.containsKey(cashID)) {
            return "No cashier found";
        }
        Cash cashier = (Cash) users.get(cashID);
        if (!cashier.getTickets().containsKey(ticketID)) {
            return "This worker did not create the ticket with this id";
        }

        Map<Integer, IProduct> products = productService.getProducts();
        IProduct product = products.get(productID);
        if (product == null) {
            return ErrorMessageHandler.getPRODUCTNOTEXIST();
        }
        CustomProduct customProduct = (CustomProduct) product;
        if (customProduct.getMaxPers() < personalizableTexts.length) {
            return "There are too many personalizable texts for this product";
        }

        // print means a successful add, otherwise it's an error message.
        String result = cashier.addProductToTicket(ticketID, productID, amount, personalizableTexts, productService);
        if ("print".equals(result)) {
            Ticket ticket = cashier.getTicket(ticketID);
            return "Ticket : " + ticketID + "\n" +
                    ticket.printTicket() +
                    "ticket add: ok\n";
        } else {
            return result;
        }
    }

    /**
     * Generates a formatted list of tickets for all cashier users.
     *
     * <p>This method retrieves users from the provided service, identifies cashiers
     * based on their user ID, sorts them, and aggregates their associated tickets
     * into a single string report.</p>
     *
     * @param userService the service used to retrieve user data
     * @return a formatted string containing the IDs and statuses of all tickets
     */
    public String ticketList(UserService userService) {
        Map<String, IUser> users = userService.getUsers();
        List<String> cashIDSorted = new ArrayList<>();
        for (String cashID : users.keySet()) {
            // A user is a cashier if the first letter of the user's ID is a letter (U)
            if (Character.isAlphabetic(cashID.charAt(0))) {
                cashIDSorted.add(cashID);
            }
        }

        Collections.sort(cashIDSorted);

        StringBuilder sb = new StringBuilder();
        sb.append("Ticket List:\n");
        for (String cashID : cashIDSorted){
            Cash cash = (Cash) users.get(cashID);
            Map<String, Ticket> tickets = cash.getTickets();
            for (Ticket ticket : tickets.values()) {
                sb.append("  ")
                        .append(ticket.getId())
                        .append(" - ")
                        .append(ticket.getStatus())
                        .append("\n");
            }
        }

        sb.append("ticket list: ok\n");
        return sb.toString();
    }

    /**
     * Removes a product from a specific ticket associated with a cashier.
     *
     * @param ticketID    The unique identifier of the ticket.
     * @param cashID      The unique identifier of the cashier.
     * @param prodID      The identifier of the product to remove from the ticket.
     * @param userService The service used to retrieve user and cashier data.
     * @return A string containing the updated ticket details if successful, or an error message
     *         if the cashier ID is invalid, the cashier is not found, or the ticket does not exist.
     */
    public String ticketRemove(String ticketID, String cashID, int prodID, UserService userService) {
        if (cashID.length() != 9 && cashID.charAt(0) != 'U') {
            return "The cashID is not valid";
        }

        Map<String, IUser> users = userService.getUsers();
        if (!users.containsKey(cashID)) {
            return "No cashier found";
        }

        Cash cashier = (Cash) users.get(cashID);
        if (!cashier.getTickets().containsKey(ticketID)) {
            return "This worker did not create the ticket with this id";
        }

        Ticket ticket = cashier.getTicket(ticketID);
        ticket.ticketRemove(prodID);

        return "Ticket : " + ticketID + "\n" +
                ticket.printTicket() +
                "ticket remove: ok\n";
    }

    /**
     * Closes an open ticket and generates a print output string.
     * <p>
     * If the ticket is open, this method updates the ticket ID with a timestamp,
     * changes the status to CLOSE, and returns the formatted receipt.
     * If the ticket is already closed, it returns the existing receipt without modification.
     *
     * @param ticketID    The unique identifier of the ticket to be processed.
     * @param cashID      The ID of the cashier (must start with 'U' and be 9 chars long).
     * @param userService The service used to retrieve user data.
     * @return A formatted String containing the ticket details if successful,
     *         or an error message if the cashier or ticket is invalid.
     */
    public String ticketPrint(String ticketID, String cashID, UserService userService) {
        if (cashID.length() != 9 && cashID.charAt(0) != 'U') {
            return "The cashID is not valid";
        }

        Map<String, IUser> users = userService.getUsers();
        if (!users.containsKey(cashID)) {
            return "No cashier found";
        }

        Cash cashier = (Cash) users.get(cashID);
        if (!cashier.getTickets().containsKey(ticketID)) {
            return "This worker did not create the ticket with this id";
        }

        Ticket ticket = cashier.getTicket(ticketID);
        String newID = ticket.getId();
        if (ticket.getStatus() != TicketStatus.CLOSE) {
            String closingTimestamp = LocalDateTime.now().format(closing);
            newID = newID + closingTimestamp;
            ticket.setId(newID);
            ticket.setStatus(TicketStatus.CLOSE);
        }

        return "Ticket : " + newID + "\n" +
                ticket.printTicket() +
                "ticket print: ok\n";
    }
}