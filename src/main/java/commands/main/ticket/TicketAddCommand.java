package commands.main.ticket;

import commands.Command;
import model.products.ProductService;
import model.tickets.TicketService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketAddCommand extends Command {
    private TicketService ticketService;
    private ProductService productService;
    public TicketAddCommand(TicketService ticketService, ProductService productService) {
        super("add");
        this.ticketService = ticketService;
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) { // Sin terminar
            // ticket add <ticketId> <cashId> <prodId> <amount> [--p<prodId1> --p<prodId2> ...]
            Pattern pattern = Pattern.compile("^ticket add (.+) (.+) (\\d+) (\\d+) (?: ((?:--p\\S+)(?:\\s+--p\\S+)*))?\\s*$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if(matcher.matches()) {
                String ticketId = matcher.group(1);
                String cashId = matcher.group(2);
                int prodId = Integer.parseInt(matcher.group(3));
                int amount = Integer.parseInt(matcher.group(4));
                if(matcher.group(5) != null) {
                    String productsPart = matcher.group(5);
                }

                result = true;
            }

        }
        return result;
    }
}
