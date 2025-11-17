package etsisi.upm.es;
import commands.Command;
import commands.main.HelpCommand;
import commands.main.UnknownCommand;
import commands.main.product.ProductCommand;
import commands.main.ticket.TicketCommand;
import commands.main.user.UserCommand;
import model.products.ProductService;
import model.tickets.TicketService;
import model.users.UserService;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    // Explanation of the used regular expressions:
    // ^ indicates the start of the string
    // $ indicates the end of the chain
    // String: ".+" (one or more characters), "[a-zA-Z]+" (only letters)
    // int: "\d+" (one or more digits)
    // float: "\d+\.\d+" (number with decimals, ex: 12.34)
    // boolean: "true|false"

    private ProductService productService;
    private TicketService tickets;
    private UserService users;
    private List<Command> commands;


    /**
     * Initializes all the required components of the shop.
     */
    protected String init() {
        String WELCOME_MESSAGE = "Welcome to the ticket module App.\n" +
                                                          "Ticket module. Type 'help' to see commands.";
        productService = new ProductService();
        tickets = new TicketService();
        users = new UserService();
        initCommands();
        return WELCOME_MESSAGE;
    }

    private void initCommands() {
        // Here we would initialize the list of commands available in the application
        this.commands=new ArrayList<>();
        commands.add(new ProductCommand(productService));
        commands.add(new TicketCommand(tickets, productService, users));
        commands.add(new HelpCommand());
        commands.add(new UserCommand(users));
    }


    protected Command applyCommand(String[] commandUni) {
        boolean found=false;
        for (Command cmd:commands) {
            found=cmd.apply(commandUni);
            if(found){
                return cmd;
            }
        }
        if(!found){
            return new UnknownCommand();
        }
        return null;
    }

    /**
     * Bye!
     */
    protected String end() {
        String GOODBYE_MESSAGE="Closing application.\n"+
                               "Goodbye!";
        return GOODBYE_MESSAGE;
    }
}
