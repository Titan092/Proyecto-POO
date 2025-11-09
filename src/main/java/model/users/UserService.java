package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.products.IProduct;
import model.users.Cash;
import model.users.IUser;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class UserService {

    private HashMap<String, IUser> users;
    private int numClients;
    private int numCash;

    public UserService(){
        this.users = new HashMap<>();
        this.numClients = 0;
        this.numCash = 0;
    }

    public void clientAdd(String name, String dni, String email, String cashId){
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

    public void clientRemove(String dni){
        if (users.containsKey(dni)){
            users.remove(dni);
            numClients--;
        }else{
            System.out.println(ErrorMessageHandler.getDNINOTEXIST());
        }
    }

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
            String clientName = clientNamesAndDniSeparated[0];
            String clientDni = clientNamesAndDniSeparated[1];
            System.out.println("Client name: "+clientName+" DNI: "+clientDni);
        }
    }

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

    public void cashRemove(String cashId){
        if (users.containsValue(cashId)){
            users.remove(cashId);
        }else{
            System.out.println(ErrorMessageHandler.getCASHIDNOTEXIST());
        }
    }

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
            String cashName = cashNameAndIdSeparated[0];
            String cashId = cashNameAndIdSeparated[1];
            System.out.println("Cash name: "+cashName+" Cash ID: "+cashId);
        }

    }



}
