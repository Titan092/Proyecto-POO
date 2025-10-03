package etsisi.upm.es;

import exceptionHandler.ErrorMessageHandler;
import model.Category;
import model.Ticket;
import model.Product;
import service.ProductService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CommandHandler {

    //Explicación de las expresiones regulares usadas:
    // ^ indica el inicio de la cadena
    // $ indica el final de la cadena
    // String: ".+" (uno o más caracteres), "[a-zA-Z]+" (solo letras)
    //int: "\d+" (uno o más dígitos)
    //float: "\d+\.\d+" (número con decimales, ej: 12.34)
    //boolean: "true|false"

    private ProductService productService;
    private Ticket ticket;

    //Prompt message
    private final String PROMPT="tUPM> ";


    protected void init() {
        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");
        productService=new ProductService();
        ticket=new Ticket();
    }



    protected void start() {
        Scanner sc=new Scanner(System.in);
        boolean continuar=true;
        while(continuar){
            System.out.print(PROMPT);
            String comando=sc.nextLine();
            String[] comandoUni=comando.split(" ");
            switch (comandoUni[0]) {
                case "help":
                    printHelp();
                    break;
                case "prod":
                    if(comandoUni.length>=2){ //Si le has pasado argumentos
                        switch (comandoUni[1]){
                            case "add":
                                // prod add <id> "<nombre>" <categoria> <precio>
                                prodAdd(comando);
                                break;
                            case "list":
                                //prod list
                                prodList(comandoUni);
                                break;
                            case "update":
                                // prod update <id> NAME|CATEGORY|PRICE <value>
                                prodUpdate(comandoUni);
                                break;
                            case "remove":
                                // prod remove <id>
                                prodRemove(comandoUni);
                                break;
                        }
                    } else {
                        System.out.println(ErrorMessageHandler.getERRORMESSAGE());
                    }
                    break;
                case "ticket":
                    if(comandoUni.length>=2){
                        switch (comandoUni[1]){
                            case "new":
                                //ticket new
                                ticket.newTicket();
                                break;
                            case "add":
                                //ticket add <prodId> <quantity>
                                ticketAdd(comandoUni);
                                break;
                            case "remove":
                                //ticket remove <prodId>
                                ticketRemove(comandoUni);
                                break;
                            case "print":
                                //ticket print
                                //discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.
                                ticketPrint(comandoUni);
                                break;
                        }
                    } else{
                        System.out.println(ErrorMessageHandler.getERRORMESSAGE());
                    }
                    break;
                case "echo":
                    echo(comando);
                    break;
                case "exit":
                    continuar = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }
        sc.close();
    }

    private void prodAdd(String comando){
        try{
            Pattern pattern = Pattern.compile("^prod add (\\d+) \"([^\"]+)\" (.+) ([\\d.]+)$");
            Matcher matcher = pattern.matcher(comando);
            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                Category categoria = Category.valueOf(matcher.group(3).toUpperCase());
                float precio = Float.parseFloat(matcher.group(4));
                if(id>0)
                    productService.prodAdd(id, nombre, categoria, precio);
            } else {
                System.out.println(ErrorMessageHandler.getERRORMESSAGE());
            }
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessageHandler.getVALIDNUMBER());
        }catch (IllegalArgumentException e) {
            System.out.println(ErrorMessageHandler.getVALIDCATEGORY());
        }
    }

    private void prodList(String[] comandoUni){
        if(comandoUni.length==2){
            productService.productList();
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    private void prodUpdate(String[] comandoUni){
        if(comandoUni.length==5 && comandoUni[2].matches("\\d+") && (comandoUni[3].equalsIgnoreCase("NAME") || comandoUni[3].equalsIgnoreCase("CATEGORY") || comandoUni[3].equalsIgnoreCase("PRICE"))){
            int id = Integer.parseInt(comandoUni[2]);
            String field = comandoUni[3];
            String value = comandoUni[4];
            productService.productUpdate(id, field, value);
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    private void prodRemove(String[] comandoUni){
        if(comandoUni.length==3 && comandoUni[2].matches("\\d+")){
            int id = Integer.parseInt(comandoUni[2]);
            productService.productRemove(id);
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    private void ticketAdd(String[] comandoUni){
        if(comandoUni.length==4 && comandoUni[2].matches("\\d+") && comandoUni[3].matches("\\d+")){
            int prodId = Integer.parseInt(comandoUni[2]);
            int quantity = Integer.parseInt(comandoUni[3]);
            ticket.addProductToTicket(prodId, quantity, productService);
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    private void ticketRemove(String[] comandoUni){
        if(comandoUni.length==3 && comandoUni[2].matches("\\d+")){
            int prodId = Integer.parseInt(comandoUni[2]);
            ticket.ticketRemove(prodId);
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    private void ticketPrint(String[] comandoUni){
        if(comandoUni.length==2){
            //Función para calcular el descuento
            float discount = 0.0f;
            ticket.printTicket(discount);
        } else System.out.println(ErrorMessageHandler.getERRORMESSAGE());
    }

    private void printHelp() {
        String SPACE="  ";
        System.out.println("Commands: ");
        System.out.println(SPACE+"prod add <id> \"<name>\" <category> <price> ");
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
        System.out.println(message);
    }



    protected void end() {
        System.out.println("Closing application.");
        System.out.println("Goodbye!");
    }





}
