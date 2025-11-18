package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketPrintCommand extends Command {
    private TicketService ticketService;
    private UserService userService;
    public TicketPrintCommand(TicketService ticketService, UserService userService) {
        super("print");
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            // ticket print <ticketId> <cashId>
            Pattern pattern = Pattern.compile("^ticket print (.+) (.+)$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if(matcher.matches()) {
                String ticketId = matcher.group(1);
                String cashId = matcher.group(2);
                this.setMessage(ticketService.ticketPrint(ticketId, cashId, userService));
            }
            result = true;
        }
        return result;
    }
}
