package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;

public class TicketPrintCommand extends Command {
    private TicketService ticketService;
    public TicketPrintCommand(TicketService ticketService) {
        super("print");
        this.ticketService = ticketService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            // TicketPrint logic to be implemented
            result = true;
        }
        return result;
    }
}
