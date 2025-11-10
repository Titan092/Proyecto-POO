package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;

public class TicketListCommand extends Command {
    private TicketService ticketService;

    public TicketListCommand(TicketService ticketService) {
        super("list");
        this.ticketService = ticketService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length == 2 && args[1].equals(this.getName())) {
            //ticketService.ticketList();
            result = true;
        }
        return result;
    }
}
