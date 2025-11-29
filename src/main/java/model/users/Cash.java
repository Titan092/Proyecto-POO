package model.users;


import model.tickets.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cash extends User {
    public Cash(String id, String name, String email){
        super(id,name,email);
    }

    public void deleteTickets(String cashID, Map<String, IUser> users){
        for (Map.Entry<String, IUser> entry : users.entrySet()){
            String key = entry.getKey();
            if (!key.isEmpty() && !Character.isAlphabetic(key.charAt(0))) {
                Client client = (Client) entry.getValue();
                Map<String, Ticket> clientTickets = client.getTickets();
                for (Map.Entry<String, Ticket> ticketEntry : clientTickets.entrySet()){
                    Ticket ticketClient = ticketEntry.getValue();
                    for (Map.Entry<String, Ticket> cashTicketEntry : tickets.entrySet()){
                        Ticket ticketCash = cashTicketEntry.getValue();
                        if (Objects.equals(ticketClient.getId(), ticketCash.getId())){
                            clientTickets.remove(ticketClient.getId());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Cash{identifier='"+super.getId()+ "'"+", name='"+super.getName()+"', email='"+super.getEmail()+"'}\n";
    }
}
