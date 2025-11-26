package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Command to create a new ticket.
 * Usage: ticket new [<id>] <cashId> <userId>
 * Needs TicketService and UserService to perform the creation.
 */
public class TicketNewCommand extends Command {
    private TicketService ticketService;
    private UserService userService;
    public TicketNewCommand(TicketService ticketService, UserService userService) {
        super("new");
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length >0 && args[1].equals(this.getName())) {
            //ticket new [<id>] <cashId> <userId>
            Pattern pattern = Pattern.compile("^ticket new(?: (.+))? (.+) (.+)$");
            Matcher m = pattern.matcher(String.join(" ", args));
            if (m.matches()) {
                String cashId = m.group(2);
                String customerId = m.group(3);
                if (m.group(1) != null) {
                    String ticketId = m.group(1);
                    this.setMessage(ticketService.ticketNew(ticketId, cashId, customerId, userService));
                } else {
                    this.setMessage(ticketService.ticketNew(cashId, customerId, userService));
                }
                result = true;
            }
        }
        return result;
    }
}
