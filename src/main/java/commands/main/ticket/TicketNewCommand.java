package commands.main.ticket;

import commands.Command;
import model.tickets.TicketService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketNewCommand extends Command {
    private TicketService ticketService;
    public TicketNewCommand(TicketService ticketService) {
        super("new");
        this.ticketService = ticketService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length >0 && args[1].equals(this.getName())) {
            //ticket new [<id>] <cashId> <userId>
            Pattern pattern = Pattern.compile("^ticket new(?:(.+))? (.+) (.+)$");
            Matcher m = pattern.matcher(String.join(" ", args));
            if (m.matches()) {
                String ticketId = m.group(2);
                String cashId = m.group(3);
                if (m.group(1) != null) {
                    String customerId = m.group(1);
                    //ticketService.ticketNew(ticketId, cashId, customerId);
                } else {
                    //ticketService.ticketNew(ticketId, cashId);
                }
                result = true;
            }

        }
        return result;
    }
}
