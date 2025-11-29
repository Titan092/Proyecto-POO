package model.users;


import model.tickets.Ticket;

import java.util.Map;
import java.util.Objects;

public class Cash extends User {
    public Cash(String id, String name, String email){
        super(id,name,email);
    }

    /**
     * Removes any tickets from Clients that match the tickets currently held
     * by this Cash register.
     *
     * <p>This method filters users based on non-alphabetic keys (typically numeric IDs)
     * and ensures the user is a Client instance before modifying their ticket list.
     *
     * @param cashID The ID of the cash register (currently unused in logic but kept for interface consistency).
     * @param users  The map of users to check against.
     */
    public void deleteTickets(String cashID, Map<String, IUser> users){
        for (Map.Entry<String, IUser> entry : users.entrySet()){
            String key = entry.getKey();
            if (!key.isEmpty() && !Character.isAlphabetic(key.charAt(0))) {
                Client client = (Client) entry.getValue();
                Map<String, Ticket> clientTickets = client.getTickets();
                for (Ticket ticketClient : clientTickets.values()){
                    for (Ticket ticketCashier : tickets.values()){
                        if (Objects.equals(ticketClient.getId(), ticketCashier.getId())){
                            clientTickets.remove(ticketClient.getId());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Cash{identifier='%s', name='%s', email='%s'}%n"
                .formatted(super.getId(), super.getName(), super.getEmail());
    }
}
