package model.users;

import model.tickets.Ticket;

import java.util.HashMap;

public class Cash extends User {

    private HashMap<String, Ticket> tickets = new HashMap<>();

    public Cash(String id, String name, String email){
        super(id,name,email);
    }


    public HashMap<String, Ticket> getCashTickets() {
        return tickets;
    }

    //Se podria poner para heredar
    public void addTicket(String cashID, Ticket ticket){
        tickets.put(cashID, ticket);
    }

}
