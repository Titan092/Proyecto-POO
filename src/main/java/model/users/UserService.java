package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.users.Cash;
import model.users.IUser;

import java.util.HashMap;
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

    }



}
