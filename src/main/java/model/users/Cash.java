package model.users;


import model.tickets.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cash extends User {
    public Cash(String id, String name, String email){
        super(id,name,email);
    }

    public void deleteTickets(String cashID, HashMap<String, IUser> users){
        if (cashID.charAt(0) != 'U'){
            System.out.println("The id is not valid");
            return;
        }else{
            for (Map.Entry<String, IUser> entry : users.entrySet()){
                String key = entry.getKey();
                if (!key.isEmpty() && !Character.isAlphabetic(key.charAt(0))) {
                    Client client = (Client) entry.getValue();
                    HashMap<String, Ticket> clientTickets = client.getTickets();
                    for (Map.Entry<String, Ticket> ticketEntry : clientTickets.entrySet()){
                        Ticket ticketClient = (Ticket) ticketEntry.getValue();
                        for (Map.Entry<String, Ticket> cashTicketEntry : tickets.entrySet()){
                            Ticket ticketCash = (Ticket) cashTicketEntry.getValue();
                            if (ticketClient.getId() == ticketCash.getId()){
                                clientTickets.remove(ticketClient.getId());
                            }
                        }


                    }
                }
            }
        }

    }

}
