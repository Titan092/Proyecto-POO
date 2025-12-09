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

/**
 * Handles the commands for the ticket module application.
 * It is the backbone of the application.
 * It initializes the services, commands, and processes user input.
 */
public class CommandHandler {

    // Explanation of the used regular expressions:
    // ^ indicates the start of the string
    // $ indicates the end of the chain
    // String: ".+" (one or more characters), "[a-zA-Z]+" (only letters)
    // int: "\d+" (one or more digits)
    // float: "\d+\.\d+" (number with decimals, ex: 12.34)
    // boolean: "true|false"

    protected ProductService productService;
    protected TicketService tickets;
    protected UserService users;
    protected List<Command> commands;


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

    /**
     * Initializes the root commands of the application.
     */
    private void initCommands() {
        // Here we would initialize the list of commands available in the application
        this.commands=new ArrayList<>();
        commands.add(new ProductCommand(productService));
        commands.add(new TicketCommand(tickets, productService, users));
        commands.add(new HelpCommand());
        commands.add(new UserCommand(users));
    }

    /**
     * Applies the command passed as a parameter.
     * @param commandUni Command to be applied in the form of an array of strings.
     * @return The command that was applied.
     */
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
