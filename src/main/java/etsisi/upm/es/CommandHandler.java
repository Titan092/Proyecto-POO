package etsisi.upm.es;

import exceptionHandler.ErrorMessageHandler;
import model.Category;
import model.Ticket;
import service.ProductService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {

    // Explanation of the used regular expressions:
    // ^ indicates the start of the string
    // $ indicates the end of the chain
    // String: ".+" (one or more characters), "[a-zA-Z]+" (only letters)
    // int: "\d+" (one or more digits)
    // float: "\d+\.\d+" (number with decimals, ex: 12.34)
    // boolean: "true|false"

    private ProductService productService;
    private Ticket ticket;

    /**
     * Prompt message
     */
    private final String PROMPT = "tUPM> ";

    /**
     * Initializes all the required components of the shop.
     */
    protected void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");
        productService = new ProductService();
        ticket = new Ticket();
    }

    /**
     * Starts the execution of the shop.
     */
    protected void start() {
        Scanner sc = new Scanner(System.in);
        boolean continues = true;
        while(continues){
            //This part is to differentiate between interactive and non-interactive mode (in the tests)
            if (!(System.in instanceof java.io.FileInputStream)) { //Here, the code comes from a file
                // If is non-interactive, does not use print
                // If is  interactive, uses print
                System.out.print(PROMPT);
            }//All this is for cleaner output in the tests
            String command = sc.nextLine();
            String[] commandUni = command.split(" ");
            switch (commandUni[0]) {
                case "help":
                    printHelp();
                    break;
                case "prod":
                    if (commandUni.length>=2) { //If you have at least 2 words
                        switch (commandUni[1]) {
                            case "add":
                                // prod add <id> "<name>" <category> <price>
                                prodAdd(command);
                                break;
                            case "list":
                                //prod list
                                prodList(commandUni);
                                break;
                            case "update":
                                // prod update <id> NAME|CATEGORY|PRICE <value>
                                prodUpdate(command);
                                break;
                            case "remove":
                                // prod remove <id>
                                prodRemove(command);
                                break;
                        }
                    } else {
                        System.out.println(ErrorMessageHandler.getERRORMESSAGE());
                    }
                    break;
                case "ticket":
                    if (commandUni.length>=2){
                        switch (commandUni[1]){
                            case "new":
                                //ticket new
                                ticket.newTicket();
                                break;
                            case "add":
                                //ticket add <prodId> <quantity>
                                ticketAdd(command);
                                break;
                            case "remove":
                                //ticket remove <prodId>
                                ticketRemove(command);
                                break;
                            case "print":
                                //ticket print
                                //discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.
                                ticket.printTicket();
                                break;
                        }
                    } else {
                        System.out.println(ErrorMessageHandler.getERRORMESSAGE());
                    }
                    break;
                case "echo":
                    echo(command);
                    break;
                case "exit":
                    continues = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }
        sc.close();
    }

    /**
     * Adds a product to productService.
     * @param command String with the command.
     */
    private void prodAdd(String command) {
        try{
            Pattern pattern = Pattern.compile("^prod add (\\d+) \"([^\"]+)\" (.+) ([\\d.]+)$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                Category category = Category.valueOf(matcher.group(3).toUpperCase());
                float price = Float.parseFloat(matcher.group(4));
                if (id >= 0) {
                    productService.prodAdd(id, name, category, price);
                }
            } else {
                System.out.println(ErrorMessageHandler.getERRORMESSAGE());
            }
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessageHandler.getVALIDNUMBER());
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorMessageHandler.getVALIDCATEGORY());
        }
    }

    /**
     * Prints all the products in productService.
     * @param commandUni String with the command.
     */
    private void prodList(String[] commandUni) {
        if (commandUni.length == 2){
            productService.productList();
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    /**
     * Updates an existing product in productService.
     * @param command String with the command.
     */
    private void prodUpdate(String command) {
        // prod update <id> NAME|CATEGORY|PRICE <value>
        Pattern pattern = Pattern.compile("^prod update (\\d+) (NAME|CATEGORY|PRICE|name|category|price) (.+)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()){
            int id = Integer.parseInt(matcher.group(1));
            String field = matcher.group(2);
            String value = matcher.group(3);
            productService.productUpdate(id, field, value);
        } else {
            System.out.println(ErrorMessageHandler.getERRORMESSAGE());
        }
    }

    /**
     * Removes a product in productService.
     * @param command String with the command.
     */
    private void prodRemove(String command) {
        // prod remove <id>
        Pattern pattern = Pattern.compile("^prod remove (\\d+)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()){
            int id = Integer.parseInt(matcher.group(1));
            productService.productRemove(id);
        } else {
            System.out.println(ErrorMessageHandler.getERRORMESSAGE());
        }
    }

    /**
     * Adds an existing product to the ticket.
     * @param command String with the command.
     */
    private void ticketAdd(String command) {
        //ticket add <prodId> <quantity>
        Pattern pattern = Pattern.compile("^ticket add (\\d+) (\\d+)$");
        Matcher matcher = pattern.matcher( command);
        if (matcher.matches()){
            int prodId = Integer.parseInt(matcher.group(1));
            int amount = Integer.parseInt(matcher.group(2));
            ticket.addProductToTicket(prodId,amount, productService);
        } else {
            System.out.println(ErrorMessageHandler.getERRORMESSAGE());
        }
    }

    /**
     * Command that removes a product from the ticket
     * @param comando String with the command.
     */
    private void ticketRemove(String comando){
        //ticket remove <prodId>
        Pattern pattern = Pattern.compile("^ticket remove (\\d+)$");
        Matcher matcher = pattern.matcher(comando);
        if (matcher.matches()){
            int prodId = Integer.parseInt(matcher.group(1));
            ticket.ticketRemove(prodId);
        } else {
            System.out.println(ErrorMessageHandler.getERRORMESSAGE());
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
    }
}
