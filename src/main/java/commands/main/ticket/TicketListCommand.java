package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;
import model.users.UserService;
/**
 * Command to list all tickets.
 * Usage: ticket list
 * Needs TicketService and UserService to perform the listing.
 */
public class TicketListCommand extends Command {
    private TicketService ticketService;
    private UserService userService;
    public TicketListCommand(TicketService ticketService, UserService userService) {
        super("list");
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length == 2 && args[1].equals(this.getName())) {
            this.setMessage(ticketService.ticketList(userService));
            result = true;
        }
        return result;
    }
}
