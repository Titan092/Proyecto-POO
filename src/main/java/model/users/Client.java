package model.users;

import model.tickets.Ticket;

import java.util.HashMap;

public class Client extends User{

    private HashMap<String, Ticket> ticketsClient = new HashMap();

    private String cashId;
    //id is the DNI of the client
    //chasId is the ID of the casher
    public Client(String name, String id, String email, String cashId){
        super(id,name,email);
        this.cashId = cashId;
    }

    String getCashId(String cashId){
        return cashId;
    }

    public void setCashId(String cashId){
        this.cashId = cashId;
    }

    

}
