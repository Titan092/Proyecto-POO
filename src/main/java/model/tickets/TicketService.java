package model.tickets;

import model.products.ProductService;
import model.users.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TicketService {
    private static final DateTimeFormatter opening = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm-");

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
            sb.append("Ticket: "+ticketID+"\n");
            sb.append("\t"+"Total price: 0.0 \n");
            sb.append("\t"+"Total discount: 0.0");
            sb.append("\t"+"Final Price: 0.0");
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
            sb.append("Ticket: "+ticketID+"\n");
            sb.append("\t"+"Total price: 0.0 \n");
            sb.append("\t"+"Total discount: 0.0");
            sb.append("\t"+"Final Price: 0.0");
            sb.append("ticket new: ok\n");
        }
        return sb.toString();
    }


    // Adds a product to a ticket
    public String ticketAdd(String ticketID, String cashID, int productID, int amount, UserService userService, ProductService productService) {
        StringBuffer sb = new StringBuffer();
        if (cashID.length() != 9 || cashID.charAt(0) != 'U'){
            sb.append("Invalid cash ID");
        }else{
            HashMap<String, IUser> users = userService.getUsers();
            Cash cash = (Cash) users.get(cashID);
            String printOrNot = cash.addProductToTicket(ticketID, productID, amount, productService);
            sb.append("Ticket: "+ticketID+"\n");
            if (printOrNot == "print"){
                ticketPrint(ticketID, cashID, userService);
            }
        }
        return sb.toString();
    }
    public String ticketAdd(String ticketID, String cashID, int productID, int amount, String[] personalizableTexts , UserService userService, ProductService productService) {
        if (cashID.charAt(0) != 'U'){
            System.out.println("Invalid cash ID");
        }else{
            HashMap<String, IUser> casherLists = userService.getUsers();
            Cash casher = (Cash) casherLists.get(cashID);
            casher.addProductToTicket(ticketID, productID, amount, productService);

        }
        return null;
    }

    public String ticketList(UserService userService) {
        StringBuffer sb = new StringBuffer();
        sb.append("Ticket List:\n");
        HashMap<String, IUser> users = userService.getUsers();
        ArrayList<String> cashIDSorted = new ArrayList<>();
        for (Map.Entry<String, IUser> entry : users.entrySet()) {
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
        sb.append("ticket list: ok");
        return sb.toString();
    }

    public String ticketRemove(String ticketID, String cashID, int prodID){
        String message = null;
        return message;
    }

    public String ticketPrint(String ticketID, String cashID, UserService userService) {
        if (cashID.length() != 9 || cashID.charAt(0) != 'U') {
            return "Invalid cash ID";
        } else {
            StringBuffer sb = new StringBuffer();
            HashMap<String, IUser> cashierLists = userService.getUsers();
            if (cashierLists.containsKey(cashID)) {
                Cash cashier = (Cash) cashierLists.get(cashID);
                Ticket ticket = cashier.getTicket(ticketID);
                if (ticket == null) {
                    return "Ticket " + ticketID + " not found for cashier " + cashID;
                } else {
                    sb.append("Ticket: " + ticketID + "\n");
                    sb.append(ticket.printTicket());
                    sb.append("ticket print: ok");
                    return sb.toString();
                }
            } else {
                return "No casher found";
            }
        }
    }

}