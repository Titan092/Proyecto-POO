package model.users;

import model.tickets.Ticket;

import java.util.HashMap;

public class Client extends User{
    private String cashId;
    //id is the DNI of the client
    //cashId is the ID of the casher
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

    @Override
    public String toString() {
       return "Client{identifier='"+super.getId()+ "'"+", name='"+super.getName()+"', email='"+super.getEmail()+"', cash="+cashId+"}\n";
    }
}
