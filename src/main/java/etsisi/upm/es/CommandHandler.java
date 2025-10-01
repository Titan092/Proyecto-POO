package etsisi.upm.es;

import model.Category;
import model.Ticket;
import model.Product;

import java.util.Scanner;

public class CommandHandler {

    //private ProductService productService;
    private Ticket ticket;
    private final String ERRORMESSAGE="There has been a typo error, please, try again";

    protected void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");
        //productService=new ProductService();
        ticket=new Ticket();
    }


    protected void start() {
        Scanner sc=new Scanner(System.in);
        boolean continuar=true;
        while(continuar){
            System.out.print("tUPM> ");
            String comando=sc.nextLine();
            String[] comandoUni=comando.split(" ");
            switch (comandoUni[0]) {
                case "help":
                    printHelp();
                    break;
                case "prod":
                    switch (comandoUni[1]){
                        case "add":
                            if(comandoUni.length>=6){
                                String[] parts=comando.split("\"");
                                String[] extra=parts[2].split(" ");
                                Category category=Category.valueOf(extra[0]);
                                Product product=new Product(Integer.parseInt(comandoUni[2]), parts[1], category, Float.parseFloat(extra[1]));
                                //Add to the ProductService object
                            } else System.out.println(ERRORMESSAGE);
                            break;
                        case "list":
                            if(comandoUni.length==2){
                                //Call to the ProductService object to list products
                            } else System.out.println(ERRORMESSAGE);
                            break;
                        case "update":
                            if(comandoUni.length==5){
                                //Call to the ProductService object to update a product
                            } else System.out.println(ERRORMESSAGE);
                            break;
                        case "remove":
                            //Call to the ProductService object to remove a product
                            break;
                    }
                    break;
                case "ticket":
                    switch (comandoUni[1]){
                        case "new":
                            //Create new ticket
                            break;
                        case "add":
                            //Add product to the ticket
                            break;
                        case "remove":
                            //Remove product from the ticket
                            break;
                        case "print":
                            //Print the ticket
                            break;
                    }
                    break;
                case "echo":
                    echo(comandoUni[1]);
                    break;
                case "exit":
                    continuar = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }
    }


    private void printHelp() {
        String SPACE="  ";
        System.out.println("Commands: ");
        System.out.println(SPACE+"prod add <id>\"<name>\" <category> <price> ");
        System.out.println(SPACE+"prod list");
        System.out.println(SPACE+"prod update <id> NAME|CATEGORY|PRICE <value>");
        System.out.println(SPACE+"prod remove <id>");
        System.out.println(SPACE+"ticket new");
        System.out.println(SPACE+"ticket add <prodId> <quantity> ");
        System.out.println(SPACE+"ticket remove <prodId>");
        System.out.println(SPACE+"ticket print");
        System.out.println(SPACE+"echo \"<texto>\"");
        System.out.println(SPACE+"help");
        System.out.println(SPACE+"exit");
        System.out.println("");
        System.out.println("Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS\n" +
                "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.");
    }



    private void unknownCommand() {
        System.out.println("Your command is not contemplated.");
        System.out.println("Type \"help\" for the command guide");
    }

    private void echo(String message){
        System.out.println("echo "+message);
    }



    protected void end() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }





}
