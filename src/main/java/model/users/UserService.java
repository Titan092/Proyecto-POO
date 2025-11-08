package model.users;

import exceptionHandler.ErrorMessageHandler;
import model.products.BaseProduct;

import java.util.HashMap;

public class UserService {

    private HashMap<String, IUser> users;
    private int numClients;
    private int numCash;

    public UserService(){
        this.users = new HashMap<>();
        this.numClients = 0;
        this.numCash = 0;
    }

    public void clientAdd(String name, String dni, String email, int cashId){

    }

    //comand for Cash with random Id
    public void cashAdd(String name, String email){
        String id;
        do{
            int numRandom = (int) (Math.random() * (9999999-1000000+1)) +1000000;
            id = "UW" + numRandom;
        }while(users.containsKey(id));
        users.put(id, new Cash(id,name,email));
        numCash++;
    }

    public void cashAdd(String id, String name, String email){
        if (!(id.length()==9)){
            System.out.println(ErrorMessageHandler.getWRONGCASHID());
        }else{
            if (!users.containsKey(id)){
                users.put(id, new Cash(id, name, email));
            }else{
                System.out.println(ErrorMessageHandler.getEXISTINGIDCASH());
            }
        }
    }
}
