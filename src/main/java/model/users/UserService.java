package model.users;

import model.products.BaseProduct;
import java.util.concurrent.ThreadLocalRandom;

public class UserService {
    public void CashAdd(String name, String email){
        do{
            int numRandom = ThreadLocalRandom.current().nextInt(1000000, 9999999+1);
            String id = "UW" + numRandom;
        }while();
    }
    public void ClientAdd(String name, String email){
        String DNI;
        do {
            int numRandom = ThreadLocalRandom.current().nextInt(10000000, 99999999+1);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            char letraDNI = letras.charAt(numRandom % 23);
            DNI = numRandom + String.valueOf(letraDNI);
        }while ();


    }
}
