package etsisi.upm.es;

import model.Ticket;
import model.Product;

import java.util.Scanner;

public class CommandHandler {

    protected void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");
    }


    protected void start() {
        Scanner sc=new Scanner(System.in);

        Ticket ticket = new Ticket();


        int numElements=0;
        boolean continuar=true;
        while(continuar){
            System.out.print("tUPM> ");
            String comando=sc.nextLine();
            String[] comandoUni=comando.split(" ");
            switch (comandoUni[0]) {
                case "help":
                    printHelp();
                    break;
                case "prod add":
                    //Code1
                    break;
                case "prod list":
                    //Code2
                    break;
                case "prod update":
                    //Code3
                    break;
                case "prod remove":
                    //Code4
                    break;
                case "ticket new":
                    //Code5
                    break;
                case "ticket add":
                    //Code6
                    break;
                case "ticket remove":
                    //Code7
                    break;
                case "ticket print":
                    //Code8
                    break;
                case "echo":
                    //Code9
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



    protected void end() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }



}
