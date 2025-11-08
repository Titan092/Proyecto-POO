package commands.main.ticket;

import commands.Command;
import model.products.ProductService;
import model.tickets.TicketService;

import java.util.ArrayList;
import java.util.List;

public class TicketCommand extends Command {
    private TicketService ticketService;
    private ProductService productService;
    private List<Command> subCommands;
    public TicketCommand(TicketService ticketService, ProductService productService) {
        super("ticket");
        this.ticketService = ticketService;
        this.productService = productService;
    }

    private void initSubCommands() {
        subCommands=new ArrayList<>();
        subCommands.add(new TicketAddCommand(ticketService, productService));
        subCommands.add(new TicketListCommand(ticketService));
        subCommands.add(new TicketRemoveCommand(ticketService));
        subCommands.add(new TicketNewCommand(ticketService));
        subCommands.add(new TicketPrintCommand(ticketService));
    }


    public boolean apply(String[] args) {
        boolean found=false;
        for (Command cmd:subCommands) {
            found=cmd.apply(args);
            if(found) break;
        }
        return found;
    }
}
