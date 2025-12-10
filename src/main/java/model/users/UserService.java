package model.users;

import etsisi.upm.es.PersistanceManager;
import exceptionHandler.ErrorMessageHandler;
import model.tickets.Ticket;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class UserService {

    private final HashMap<String, IUser> users;
    private int numClients;
    private int numCash;

    /**
     * UserService constructor
     */
    public UserService(){
        this.users = PersistanceManager.loadUsers();
        this.numClients = 0;
        this.numCash = 0;
        if(users.size()>0){
            for (Map.Entry<String, IUser> entry : users.entrySet()){
                if (Character.isDigit(entry.getKey().charAt(8))){ //if last character is a number its a dni, so its a client
                    numClients++;
                }else{
                    numCash++;
                }
            }
        }

    }

    public HashMap<String, IUser> getUsers() {
        return users;
    }

    /**
     * Registers a new client.
     * @param name   The full name of the client.
     * @param dni    The identification number.
     * @param email  The email address of the client.
     * @param cashId The ID of the cashier/entity authorizing this operation.
     * @return A success message comprising the client details, or an error message if validation fails.
     */
    public String clientAdd(String name, String dni, String email, String cashId){//Creo que aqui habria que hacer algo para lo de que los cajeros puedan ser clientes pero con otro correo
        if (!users.containsKey(cashId)) {
            return "The cash does not exist";
        }

        if ((dni.length() != 9) || !Character.isAlphabetic(dni.charAt(8))) {
            return ErrorMessageHandler.getWRONGDNIFORMAT();
        }

        if (Character.isDigit(dni.charAt(8))) {
            return ErrorMessageHandler.getWRONGDNIFORMAT();
        }

        Client client = new Client(name, dni, email, cashId);
        users.put(dni, client);
        numClients++;
        return client + "client add: ok\n";
    }

    /**
     * Removes a client from the system based on the provided DNI.
     *
     * @param dni The DNi of the client.
     * @return A status message indicating success ("client remove: ok") or the specific error.
     */
    public String clientRemove(String dni) {
        if (!Character.isAlphabetic(dni.charAt(8)) || dni.length() != 9){
            return  "Invalid ID";
        }
        if (!users.containsKey(dni)) {
            return ErrorMessageHandler.getDNINOTEXIST();
        }
        users.remove(dni);
        numClients--;
        return  "client remove: ok\n";
    }

    /**
     * Print the list of client, showing their name first and then their DNI.
     */
    public String clientList(){
        StringBuffer sb = new StringBuffer();
        sb.append("Client:\n");
        //Put the name and Dni in a list
        ArrayList<String> clientNamesAndDni = new ArrayList<>();
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
            Client client = (Client) users.get(clientNamesAndDniSeparated[1]); //field [1] its the id used to get the client
            sb.append("  "+client.toString());
        }
        sb.append("client list: ok\n");
        return sb.toString();
    }

    /**
     * Creates the cash with a random cash ID and adds it to the HashMap
     *
     * @param name
     * @param email
     */
    //comand for Cash with random Id
    public String cashAdd(String name, String email){
        String id, message;
        do{
            int numRandom = ThreadLocalRandom.current().nextInt(1000000, 9999999+1);
            id = "UW" + numRandom;
        }while(users.containsKey(id));
        Cash cash = new Cash(id, name, email);
        users.put(id, cash);
        numCash++;
        message = (cash + "cash add: ok\n");
        return message;
    }

    /**
     * Creates the cash and adds it to the HashMap
     *
     * @param cashId
     * @param name
     * @param email
     */
    public String cashAdd(String cashId, String name, String email){
        String message = null;
        if (!(cashId.length()==9)){
            message=ErrorMessageHandler.getWRONGCASHID();
        }else{
            if (!users.containsKey(cashId)){
                Cash cash = new Cash(cashId, name, email);
                users.put(cashId, cash);
                numCash++;
                message = (cash + "cash add: ok\n");
            }else{
                message=ErrorMessageHandler.getEXISTINGIDCASH();
            }
        }
        return message;
    }

    /**
     * Delete the cash with the ID passed as a parameter.
     *
     * @param cashId
     */
    public String cashRemove(String cashId){
        String message;
        if (cashId.charAt(0) != 'U' || cashId.length() != 9){
            message = "The id is not valid";
        }else{
            if (users.containsKey(cashId)){
                Cash cash = (Cash) users.get(cashId);
                cash.deleteTickets(cashId, users);
                users.remove(cashId);
                message = "cash remove: ok\n";
            }else{
                message=ErrorMessageHandler.getCASHIDNOTEXIST();
            }
        }
        return message;
    }


    /**
     * Print the list of cash, showing their name first and then their cash ID.
     */
    public String cashList(){
        StringBuffer sb = new StringBuffer();
        sb.append("Cash:\n");
        //Put the name and the cashId in a list
        ArrayList<String> cashNameAndId = new ArrayList<>();
        for (Map.Entry<String, IUser> entry : users.entrySet()){
            if (Character.isAlphabetic(entry.getKey().charAt(0))){//if the first character is alphabetic (U from UW) is a chash
                String nameAndCashId = (entry.getValue().getName())+" "+ (entry.getValue().getId());
                cashNameAndId.add(nameAndCashId);
            }
        }

        //Sort the names alphabetically
        Collections.sort(cashNameAndId);

        for (int i=0;i<cashNameAndId.size();i++){
            //separate the names and dnis
            String [] cashNameAndIdSeparated = cashNameAndId.get(i).split(" ");
            Cash cash = (Cash) users.get(cashNameAndIdSeparated[1]); //field [1] its the id used to get the client
            sb.append("  "+cash.toString());
        }
        sb.append("cash list: ok\n");
        return sb.toString();
    }



    /**
     * Prints the tickets created by the cashier with the ID passed as a parameter, sorted by ticket ID and status.
     *
     * @param cashId
     */
    public String cashTickets(String cashId){
        StringBuffer sb = new StringBuffer();
        if (cashId.charAt(0) == 'U' && cashId.length() == 9){
            sb.append("Tickets: \n");
            if (users.containsKey(cashId)){
                Cash cash = (Cash) users.get(cashId);
                Map<String, Ticket> tickets = cash.getTickets();
                ArrayList<String> ticketIDs = new ArrayList<>();
                for (Map.Entry<String, Ticket> entry : tickets.entrySet()){
                    String idTicketAndStatus = entry.getValue().getId() + " " + entry.getValue().getStatus();
                    ticketIDs.add(idTicketAndStatus);
                }

                //Sort by ticket ID
                Collections.sort(ticketIDs);

                for (String ticketID : ticketIDs){
                    String[]  ticketIDSeparated = ticketID.split(" ");
                    sb.append("  "+ticketIDSeparated[0]+"->"+ticketIDSeparated[1]+"\n");
                }
                sb.append("cash tickets: ok\n");
            }else{
                return "The cash does not exist";
            }

        }
        return sb.toString();
    }
}