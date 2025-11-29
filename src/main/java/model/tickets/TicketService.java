package model.tickets;

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

    public String ticketNew(String cashID, String clientID, UserService userService) {
        StringBuffer sb = new StringBuffer();
        HashMap<String, IUser> casherLists = userService.getUsers();
        if (clientID.length() != 9 || !Character.isAlphabetic(clientID.charAt(8))){
            sb.append("The clientID is not valid");
        } else if (cashID.length() != 9 || cashID.charAt(0) != 'U') {
            sb.append("The cashID is not valid");
        }else{
            int numRandom = ThreadLocalRandom.current().nextInt(10000, 99999+1);
            String ticketID = LocalDateTime.now().format(opening) + numRandom;
            Ticket ticket = new Ticket(ticketID);
            Cash casher = (Cash) casherLists.get(cashID);
            casher.newTicket(ticketID, ticket);
            Client client = (Client) casherLists.get(clientID);
            client.newTicket(ticketID, ticket);
            sb.append("Ticket : "+ticketID+"\n");
            sb.append("  "+"Total price: 0.0\n");
            sb.append("  "+"Total discount: 0.0\n");
            sb.append("  "+"Final Price: 0.0\n");
            sb.append("ticket new: ok\n");
        }
        return sb.toString();
    }

    public String ticketNew(String ticketID, String cashID, String clientID, UserService userService) {
        StringBuffer sb = new StringBuffer();
        HashMap<String, IUser> casherLists = userService.getUsers();
        if (clientID.length() != 9 || !Character.isAlphabetic(clientID.charAt(8))){
            sb.append("The clientID is not valid");
        } else if (cashID.length() != 9 || cashID.charAt(0) != 'U') {
            sb.append("The cashID is not valid");
        }else{
            Ticket ticket = new Ticket(ticketID);
            Cash casher = (Cash) casherLists.get(cashID);
            casher.newTicket(ticketID, ticket);
            Client client = (Client) casherLists.get(clientID);
            client.newTicket(ticketID, ticket);
            sb.append("Ticket : " + ticketID + "\n");
            sb.append("  " + "Total price: 0.0\n");
            sb.append("  " + "Total discount: 0.0\n");
            sb.append("  " + "Final Price: 0.0\n");
            sb.append("ticket new: ok\n");
        }
        return sb.toString();
    }


    // Adds a product to a ticket
    public String ticketAdd(String ticketID, String cashID, int productID, int amount, UserService userService, ProductService productService) {
        if (cashID.length() == 9 && cashID.charAt(0) == 'U') {
            HashMap<String, IUser> users = userService.getUsers();
            if (users.containsKey(cashID)) {
                Cash cashier = (Cash) users.get(cashID);
                if (cashier.getTickets().containsKey(ticketID)) {
                    Ticket ticket = cashier.getTicket(ticketID);
                    String printOrNot = cashier.addProductToTicket(ticketID, productID, amount, productService);
                    if (Objects.equals(printOrNot, "print")) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("Ticket : " + ticketID + "\n");
                        sb.append(ticket.printTicket());
                        sb.append("ticket add: ok\n");
                        return sb.toString();
                    } else {
                        return printOrNot;
                    }
                } else {
                    return "This worker did not create the ticket with this id";
                }
            } else {
                return "No cashier found";
            }
        }
        return "The cashID is not valid";
    }



    public String ticketAdd(String ticketID, String cashID, int productID, int amount, String[] personalizableTexts, UserService userService, ProductService productService) {
        if (cashID.length() == 9 && cashID.charAt(0) == 'U') {
            HashMap<String, IUser> users = userService.getUsers();
            if (users.containsKey(cashID)) {
                Cash cashier = (Cash) users.get(cashID);
                if (cashier.getTickets().containsKey(ticketID)) {
                    HashMap<Integer, IProduct> products = productService.getProducts();
                    CustomProduct customProduct = (CustomProduct) products.get(productID);
                    if (customProduct.getMaxPers() >= personalizableTexts.length) {
                        Ticket ticket = cashier.getTicket(ticketID);
                        String printOrNot = cashier.addProductToTicket(ticketID, productID, amount, personalizableTexts, productService);
                        if (Objects.equals(printOrNot, "print")) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("Ticket : " + ticketID + "\n");
                            sb.append(ticket.printTicket());
                            sb.append("ticket add: ok\n");
                            return sb.toString();
                        } else {
                            return printOrNot;
                        }
                    } else {
                        return "There are too many personalizable texts for this product";
                    }
                } else {
                    return "This worker did not create the ticket with this id";
                }
            } else {
                return "No cashier found";
            }
        }
        return "The cashID is not valid";
    }

    public String ticketList(UserService userService) {
        StringBuffer sb = new StringBuffer();
        sb.append("Ticket List:\n");
        HashMap<String, IUser> users = userService.getUsers();
        ArrayList<String> cashIDSorted = new ArrayList<>();
        for (Map.Entry<String, IUser> entry : users.entrySet()) {
            if (entry)
            String key = entry.getKey();
            if (!key.isEmpty() && Character.isAlphabetic(key.charAt(0))) {//if the first character is alphabetic (U from UW) is a chash
                cashIDSorted.add(entry.getValue().getId());
            }
        }
        //Sort by cashID
        Collections.sort(cashIDSorted);

        for (String cashID : cashIDSorted){
            Cash cash = (Cash) users.get(cashID);
            HashMap<String, Ticket> tickets = cash.getTickets();
            for (Map.Entry<String, Ticket> entry : tickets.entrySet()){
                sb.append(entry.getValue().getId()+" - "+entry.getValue().getStatus()+"\n");
            }
        }
        sb.append("ticket list: ok\n");
        return sb.toString();
    }

    public String ticketRemove(String ticketID, String cashID, int prodID, UserService userService){
        if (cashID.length() == 9 && cashID.charAt(0)== 'U'){
            HashMap<String, IUser> users = userService.getUsers();
            if (users.containsKey(cashID)){
                Cash cash = (Cash) users.get(cashID);
                if (cash.getTickets().containsKey(ticketID)){ //es el creador del ticket con la id pasada por parametro
                    Ticket ticket = cash.getTicket(ticketID);
                    ticket.ticketRemove(prodID);
                    StringBuffer sb = new StringBuffer();
                    sb.append("Ticket : "+ticketID+"\n");
                    sb.append(ticket.printTicket());
                    sb.append("ticket remove: ok\n");
                    return sb.toString();
                }else{
                    return "This worker did not create the ticket with this id";
                }
            }else{
                return "No cashier found";
            }
        }
        return "The cashID is not valid";
    }

    public String ticketPrint(String ticketID, String cashID, UserService userService) {
        if (cashID.length() == 9 && cashID.charAt(0) == 'U') {
            HashMap<String, IUser> users = userService.getUsers();
            if (users.containsKey(cashID)) {
                Cash cashier = (Cash) users.get(cashID);
                if (cashier.getTickets().containsKey(ticketID)) {
                    Ticket ticket = cashier.getTicket(ticketID);
                    StringBuffer sb = new StringBuffer();
                    String newID = null;
                    if (ticket.getStatus() != TicketStatus.CLOSE) {
                        String closingTimestamp = LocalDateTime.now().format(closing);
                        String id = ticket.getId();
                        newID = id + closingTimestamp;
                        ticket.setId(newID);
                    }
                    sb.append("Ticket : " + newID + "\n");
                    sb.append(ticket.printTicket());
                    sb.append("ticket print: ok\n");
                    if (ticket.getStatus() != TicketStatus.CLOSE) {
                        ticket.setStatus(TicketStatus.CLOSE);
                        return sb.toString();
                    }
                } else {
                    return "This worker did not create the ticket with this id";
                }
            } else {
                return "No cashier found";
            }
        }
        return "The cashID is not valid";
    }
}