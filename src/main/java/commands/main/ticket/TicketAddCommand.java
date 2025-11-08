package commands.main.ticket;

import commands.Command;
import model.products.ProductService;
import model.tickets.TicketService;

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
        if(args.length > 0 && args[1].equals(this.getName())) {
            //TicketAdd logic to be implemented
            result = true;
        }
        return result;
    }
}
