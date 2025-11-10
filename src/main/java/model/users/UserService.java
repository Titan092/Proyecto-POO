package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.products.IProduct;
import model.tickets.Ticket;
import model.users.Cash;
import model.users.IUser;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class UserService {

    private HashMap<String, IUser> users;
    private int numClients;
    private int numCash;

    /**
     * UserService constructor
     */
    public UserService(){
        this.users = new HashMap<>();
        this.numClients = 0;
        this.numCash = 0;
    }

    /**
     * Creates the client and adds it to the HashMap
     * @param name
     * @param dni
     * @param email
     * @param cashId
     */
    public void clientAdd(String name, String dni, String email, String cashId){//Creo que aqui habria que hacer algo para lo de que los cajeros puedan ser clientes pero con otro correo
        if ((dni.length()==9)){
           if (!Character.isDigit(dni.charAt(8))){
               users.put(dni,new Client(name,dni,email,cashId));
               numClients++;
           }else{
               System.out.println(ErrorMessageHandler.getWRONGDNIFORMAT());
           }
        }else{
            System.out.println(ErrorMessageHandler.getWRONGDNIFORMAT());
        }
    }

    /**
     * Delete the client with the ID passed as a parameter.
     * @param dni
     */
    public void clientRemove(String dni){
        if (users.containsKey(dni)){
            users.remove(dni);
            numClients--;
        }else{
            System.out.println(ErrorMessageHandler.getDNINOTEXIST());
        }
    }

    /**
     * Print the list of client, showing their name first and then their DNI.
     */
    public void clientList(){

        //Put the name and Dni in a list
        List<String> clientNamesAndDni = new ArrayList<>();
        for (Map.Entry<String, IUser> entry : users.entrySet()){
          if (!Character.isDigit(entry.getKey().charAt(8))){ //if last character is not a number its a dni, so its a client
              String nameAndDni = (entry.getValue().getName())+" "+(entry.getValue().getId());
              clientNamesAndDni.add(nameAndDni);
          }
        }

        //Sort the names alphabetically
        Collections.sort(clientNamesAndDni);

        for (int i=0;i<clientNamesAndDni.size();i++){
            //separate the names and dnis
            String [] clientNamesAndDniSeparated = clientNamesAndDni.get(i).split(" ");
            System.out.println("Client name: "+clientNamesAndDniSeparated[0]+" DNI: "+clientNamesAndDniSeparated[1]);
        }
    }

    /**
     * Creates the cash with a random cash ID and adds it to the HashMap
     * @param name
     * @param email
     */
    //comand for Cash with random Id
    public void cashAdd(String name, String email){
        String id;
        do{
            int numRandom = ThreadLocalRandom.current().nextInt(1000000, 9999999+1);
            id = "UW" + numRandom;
        }while(users.containsKey(id));
        users.put(id, new Cash(id,name,email));
        numCash++;
    }

    /**
     * Creates the cash and adds it to the HashMap
     * @param cashId
     * @param name
     * @param email
     */
    public void cashAdd(String cashId, String name, String email){
        if (!(cashId.length()==9)){
            System.out.println(ErrorMessageHandler.getWRONGCASHID());
        }else{
            if (!users.containsKey(cashId)){
                users.put(cashId, new Cash(cashId, name, email));
            }else{
                System.out.println(ErrorMessageHandler.getEXISTINGIDCASH());
            }
        }
    }

    /**
     * Delete the cash with the ID passed as a parameter.
     * @param cashId
     */
    public void cashRemove(String cashId){
        if (users.containsValue(cashId)){
            users.remove(cashId);
        }else{
            System.out.println(ErrorMessageHandler.getCASHIDNOTEXIST());
        }
    }

    /**
     * Print the list of cash, showing their name first and then their cash ID.
     */
    public void cashList(){
        //Put the name and the cashId in a list
        List<String> cashNameAndId = new ArrayList<>();
        for (Map.Entry<String, IUser> entry : users.entrySet()){
            if (Character.isAlphabetic(0)){//if the first character is alphabetic (U from UW) is a chash
                String nameAndCashId = (entry.getValue().getName())+" "+ (entry.getValue().getId());
                cashNameAndId.add(nameAndCashId);
            }
        }

        //Sort the names alphabetically
        Collections.sort(cashNameAndId);

        for (int i=0;i<cashNameAndId.size();i++){
            //separate the names and dnis
            String [] cashNameAndIdSeparated = cashNameAndId.get(i).split(" ");
            System.out.println("Cash name: "+cashNameAndIdSeparated[0]+" Cash ID: "+cashNameAndIdSeparated[1]);
        }

    }

    /**
     * Prints the tickets created by the cashier with the ID passed as a parameter, sorted by ticket ID and status.
     * @param cashId
     * @param ticket
     */
    public void cashTickets(String cashId, Ticket ticket){
        ArrayList<String> listTicketID = new ArrayList<>();
        IProduct [] ticketItems = ticket.getTicketItems();
        for (int i=0; i< ticketItems.length;i++){
            if (ticketItems[i].getId().equals(cashId)){
                String idTicketAndStatus = (ticketItems[i].getId())+" "+(ticket.getTicketStatus());
                listTicketID.add(idTicketAndStatus);
            }
        }
        //Sort by ticket ID
        Collections.sort(listTicketID);

        for (int i=0; i< listTicketID.size();i++){
            String [] separatedTicketIdAndStatus = listTicketID.get(i).split(" ");
            System.out.println("Ticket id: "+ separatedTicketIdAndStatus[0]+" Ticket Status: "+ separatedTicketIdAndStatus[1]);
        }
    }
}

