package model.products;

import exceptionHandler.ErrorMessageHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProductService {

    private HashMap<Integer,IProduct> products;
    private final int MAX_QUANTITY = 200;
    private int numProducts;

    public ProductService(){//Relacion agregación
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
        boolean found = false;
        int i = 0;
        while (!found && i < MAX_QUANTITY) {
            if (products[i] != null && products[i].getId() == id) {
                switch (field.toUpperCase()) {
                    case "NAME":
                        products[i].setName(value);
                        break;
                    case "CATEGORY":
                        try {
                            Category categoryNew = Category.valueOf(value.toUpperCase());
                            products[i].setCategory(categoryNew);
                            System.out.println("Category successfully updated.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(ErrorMessageHandler.getVALIDCATEGORY());
                        }
                        break;
                    case "PRICE":
                        try{
                            float priceValue = Float.parseFloat(value);
                            products[i].setPrice(priceValue);
                        } catch (NumberFormatException e) {
                            System.out.println(ErrorMessageHandler.getVALIDNUMBER());
                        }
                        break;
                    default:
                        System.out.println(ErrorMessageHandler.getFIELDERROR());
                        break;
                }
                System.out.println(products[i].toString());
                System.out.println("prod update: ok");
                found = true;
            }
            i++;
        }
        if (!found) {
            System.out.println(ErrorMessageHandler.getIDNOTEXIST());
        }
    }

    public void prodList(){
        System.out.println("Catalog:");
        for (Map.Entry<Integer,IProduct> entry : products.entrySet()){
            System.out.println(products.get(entry.getValue()).toString());
        }


    }

    public void prodRemove(int id) {
        if (id < 0) {
            System.out.println(ErrorMessageHandler.getWRONGID());
        } else {
            if (!products.containsKey(id)) {
                System.out.println(ErrorMessageHandler.getNOTFINDGID());
            } else {
                products.remove(id);
                numProducts--;
            }
        }
    }
}


