package etsisi.upm.es;

import commands.Command;
import commands.main.HelpCommand;
import commands.main.product.ProductCommand;
import commands.main.ticket.TicketCommand;
import commands.main.user.UserCommand;
import model.products.ProductService;
import model.tickets.TicketService;
import model.users.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private Scanner s;
    private List<Command> commands;

    /**
     * Prompt message
     */
    private final String PROMPT = "tUPM> ";

    /**
     * Initializes all the required components of the shop.
     */
    protected void init(Scanner s) {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");
        productService = new ProductService();
        tickets = new TicketService();
        users = new UserService();
        this.s = s;
        initCommands();
    }

    private void initCommands() {
        // Here we would initialize the list of commands available in the application
        this.commands=new ArrayList<>();
        commands.add(new ProductCommand(productService));
        commands.add(new TicketCommand());
        commands.add(new HelpCommand());
        commands.add(new UserCommand());
    }

    /**
     * Starts the execution of the shop.
     */
    protected void start() {
        boolean continues = true;
        while(continues) {
            System.out.print(PROMPT);
            //This part is to differentiate between interactive and non-interactive mode (in the tests)
            //All this is for cleaner output in the tests
            String command = s.nextLine();
            if (System.in instanceof java.io.FileInputStream) { //Here, the code comes from a file
                System.out.println(command);
            }
            String[] commandUni = command.split(" ");
            switch (commandUni[0]){
                case "echo":
                    echo(command);
                    break;
                case "exit":
                    continues=false;
                    break;
                default:
                    applyCommand(commandUni);
            }
        }
    }


    private void applyCommand(String[] commandUni) {
        boolean found=false;
        for (Command cmd:commands) {
            found=cmd.apply(commandUni);
        }
        if(!found){
            unknownCommand();
        }
    }







    /**
     * Prints all the available commands.
     */
    private void printHelp() {
        String SPACE="  ";
        System.out.println("Commands: ");
        System.out.println(SPACE + "prod add <id> \"<name>\" <category> <price> ");
        System.out.println(SPACE + "prod list");
        System.out.println(SPACE + "prod update <id> NAME|CATEGORY|PRICE <value>");
        System.out.println(SPACE + "prod remove <id>");
        System.out.println(SPACE + "ticket new");
        System.out.println(SPACE + "ticket add <prodId> <quantity> ");
        System.out.println(SPACE + "ticket remove <prodId>");
        System.out.println(SPACE + "ticket print");
        System.out.println(SPACE + "echo \"<texto>\"");
        System.out.println(SPACE + "help");
        System.out.println(SPACE + "exit");
        System.out.println();
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS\n" +
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.");
    }

    /**
     * In case the user enters an unknown command, prints a helpful message.
     */
    private void unknownCommand() {
        System.out.println("Your command is not contemplated.");
        System.out.println("Type \"help\" for the command guide");
    }

    /**
     * Prints whatever the user had entered before.
     * @param message Message the user entered.
     */
    private void echo(String message){
        System.out.println(message);
    }

    /**
     * Bye!
     */
    protected void end() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
        s.close();
    }
}
