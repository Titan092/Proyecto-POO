package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketRemoveCommand extends Command {
    private TicketService ticketService;
    private UserService userService;
    public TicketRemoveCommand(TicketService ticketService, UserService userService) {
        super("remove");
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            // ticket remove <ticketId><cashId> <prodId>
            Pattern pattern = Pattern.compile("^ticket remove (.+) (.+) (\\d+)$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if(matcher.matches()) {
                String ticketId = matcher.group(1);
                String cashId = matcher.group(2);
                int prodId = Integer.parseInt(matcher.group(3));
                this.setMessage(ticketService.ticketRemove(ticketId, cashId, prodId, userService));
            }
            result = true;
        }
        return result;
    }
}
