package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;

public class TicketNewCommand extends Command {
    private TicketService ticketService;
    public TicketNewCommand(TicketService ticketService) {
        super("new");
        this.ticketService = ticketService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length >0 && args[1].equals(this.getName())) {
            // TicketNew logic to be implemented
            result = true;
        }
        return result;
    }
}
