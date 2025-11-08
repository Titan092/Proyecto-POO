package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;

public class TicketRemoveCommand extends Command {
    TicketService ticketService;
    public TicketRemoveCommand(TicketService ticketService) {
        super("remove");
        this.ticketService = ticketService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            // TicketRemove logic to be implemented
            result = true;
        }
        return result;
    }
}
