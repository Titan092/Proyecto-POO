package commands.main.ticket;

import commands.Command;
import model.products.ProductService;
import model.tickets.TicketService;
import model.users.UserService;

import java.util.ArrayList;
import java.util.List;

public class TicketCommand extends Command {
    private TicketService ticketService;
    private ProductService productService;
    private UserService userService;
    private List<Command> subCommands;
    public TicketCommand(TicketService ticketService, ProductService productService, UserService userService) {
        super("ticket");
        this.ticketService = ticketService;
        this.productService = productService;
        this.userService = userService;
        initSubCommands();
    }

    private void initSubCommands() {
        subCommands=new ArrayList<>();
        subCommands.add(new TicketAddCommand(ticketService, productService, userService));
        subCommands.add(new TicketListCommand(ticketService, userService));
        subCommands.add(new TicketRemoveCommand(ticketService, userService));
        subCommands.add(new TicketNewCommand(ticketService, userService));
        subCommands.add(new TicketPrintCommand(ticketService, userService));
    }


    public boolean apply(String[] args) {
        boolean found=false;
        if(args[0].equals(this.getName())) {
            for (Command cmd:subCommands) {
                found=cmd.apply(args);
                if(found){
                    this.setMessage(cmd.getMessage());
                    break;
                }
            }
        }
        return found;
    }
}
