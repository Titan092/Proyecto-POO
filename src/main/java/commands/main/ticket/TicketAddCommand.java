package commands.main.ticket;

import commands.Command;
import model.products.ProductService;
import model.tickets.TicketService;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Command to add items to a ticket.
 * Usage: ticket add <ticketId> <cashId> <prodId> <amount> [--p<txt> --p<txt>]
 * Needs TicketService, ProductService, and UserService to perform the addition.
 */
public class TicketAddCommand extends Command {
    private TicketService ticketService;
    private ProductService productService;
    private UserService userService;
    public TicketAddCommand(TicketService ticketService, ProductService productService, UserService userService) {
        super("add");
        this.ticketService = ticketService;
        this.productService = productService;
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            // ticket add <ticketId> <cashId> <prodId> <amount> [--p<txt> --p<txt>]
            Pattern pattern = Pattern.compile("^ticket add (.+) (.+) (\\d+) (\\d+)(.*)");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if(matcher.matches()) {
                String ticketId = matcher.group(1);
                String cashId = matcher.group(2);
                int prodId = Integer.parseInt(matcher.group(3));
                int amount = Integer.parseInt(matcher.group(4));
                boolean allok=true;
                if(args.length>6) {
                    String[] personalizable=new String[args.length-6];
                    for (int i = 6; i < args.length; i++) {
                        if (args[i].startsWith("--p")) {
                            personalizable[i-6]=args[i].substring(3);
                        }else{
                            allok=false;
                            break;
                        }
                    }
                    this.setMessage(ticketService.ticketAdd(ticketId, cashId, prodId, amount, personalizable, userService, productService));
                }else{
                    this.setMessage(ticketService.ticketAdd(ticketId, cashId, prodId, amount, userService, productService));
                }
                result = allok;
            }
        }
        return result;
    }
}
