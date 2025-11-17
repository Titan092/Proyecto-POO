package model.tickets;

import model.products.ProductService;
import model.users.Cash;
import model.users.Client;
import model.users.IUser;
import model.users.UserService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TicketService {
    private static final DateTimeFormatter opening = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm-");
    private final int MAX_TICKETS = 100;
    private int numTickets = 0;

    public Ticket ticketNew(String ticketID, String cashID, UserService userService) {
        HashMap<String, IUser> casherLists = userService.getUsers();
        Ticket ticket = new Ticket(ticketID);
        Cash casher = (Cash) casherLists.get(cashID);
        casher.newTicket(ticketID, ticket);
        return ticket;
    }

    public Ticket ticketNew(String ticketID, String cashID, String clientID, UserService userService) {
        HashMap<String, IUser> casherLists = userService.getUsers();
        Ticket ticket = new Ticket(ticketID);
        Cash casher = (Cash) casherLists.get(cashID);
        casher.newTicket(ticketID, ticket);
        Client client = (Client) casherLists.get(clientID);
        client.newTicket(ticketID, ticket);
        return ticket;
    }

    // Adds a product to a ticket
    public void ticketAdd(String ticketID, String cashID, int productID, int amount, UserService userService, ProductService productService) {
        if (cashID.charAt(0) != 'U'){
            System.out.println("Invalid cash ID");
        }else{
            HashMap<String, IUser> casherLists = userService.getUsers();
            Cash casher = (Cash) casherLists.get(cashID);
            casher.addProductToTicket(ticketID, productID, amount, productService);
        }
    }
    public void ticketAdd(String ticketID, String cashID, int productID, int amount, String[] personalizableTexts , UserService userService, ProductService productService) {
        if (cashID.charAt(0) != 'U'){
            System.out.println("Invalid cash ID");
        }else{
            HashMap<String, IUser> casherLists = userService.getUsers();
            Cash casher = (Cash) casherLists.get(cashID);
            casher.addProductToTicket(ticketID, productID, amount, productService);

        }
    }

    public void ticketList(UserService userService) {
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
                entry.getValue().printTicket();
            }
        }
    }
}