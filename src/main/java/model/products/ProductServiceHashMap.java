package model.products;

import exceptionHandler.ErrorMessageHandler;

import java.util.Date;
import java.util.HashMap;

public class ProductServiceHashMap {

    private HashMap<Integer,IProduct> products;
    private final int MAX_QUANTITY = 200;
    private int numProducts;

    public ProductServiceHashMap(){//Relacion agregación
        this.products = new HashMap<>();
        this.numProducts = 0;
    }

    //command for baseProduct
    public void prodAdd(int id, String name, Category category ,float price){
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new BaseProduct(id, name , category, price));
                    numProducts++;
                }
            }else{
                System.out.println(ErrorMessageHandler.getEXISTINGID());
            }
        }
    }

    //command for CustomProduct (overload)
    public void prodAdd(int id, String name, Category category, float price, int maxPers){
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new CustomProduct(id,name,category,price,maxPers));
                    numProducts++;
                }
            }else{
                System.out.println(ErrorMessageHandler.getEXISTINGID());
            }
        }
    }

    public void prodAddFood(int id, String name, float price, Date date, int maxPeople){
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new Food(id,name,price,date,maxPeople));
                    numProducts++;
                }
            }else{
                System.out.println(ErrorMessageHandler.getEXISTINGID());
            }
        }
    }

    public void prodAddMeeting(int id, String name, float price, Date date, int maxPeople){
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new Meeting(id,name,price,date,maxPeople));
                    numProducts++;
                }
            }else{
                System.out.println(ErrorMessageHandler.getEXISTINGID());
            }
        }
    }


    public void prodUpdate(int id, String field, String value){

    }

    public void prodList(){
        System.out.println("Catalog:");
        products.forEach();


        }
    }

    public void prodRemove(int id){
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }else{
            if (!products.containsKey(id)){
                System.out.println(ErrorMessageHandler.getNOTFINDGID());
            }else{
                products.remove(id);
                numProducts--;
            }
        }
    }


}
