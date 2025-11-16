package model.users;

import model.tickets.Ticket;

import java.util.ArrayList;

public abstract class User implements IUser {

    private String id;
    private String name;
    private String email;
    // Every user has a list of tickets associated with it
    private ArrayList<Ticket> tickets;

    public User(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
        this.tickets = new ArrayList<>();
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

    public void insertTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }


}
