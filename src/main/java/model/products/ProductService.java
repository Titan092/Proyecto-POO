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

    //comand for baseProduct with random ID
    public void prodAdd(String name, Category category, float price){
        int id = (int) (Math.random() * (9999999-1000000+1)) +1000000;


    }


    //command for baseProduct with explicit ID
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
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }else{
            if (!products.containsKey(id)){
                System.out.println(ErrorMessageHandler.getIDNOTEXIST());
            }else{
                IProduct product = products.get(id); //the product that is going to be updated
                switch (field.toUpperCase()){
                    case "NAME":
                        product.setName(value);
                        break;
                    case "CATEGORY":
                        try{
                            Category categoryNew = Category.valueOf(value.toUpperCase());
                            if (product instanceof ICategorizable){
                                ((ICategorizable) product).setCategory(categoryNew);
                            }else{
                                System.out.println("This type of product do not have category");
                            }
                        }catch (IllegalArgumentException e){
                            System.out.println(ErrorMessageHandler.getVALIDCATEGORY());
                        }
                        break;
                    case "PRICE":
                        try{
                            float priceValue = Float.parseFloat(value);
                            product.setPrice(priceValue);
                        }catch (NumberFormatException e) {
                            System.out.println(ErrorMessageHandler.getVALIDNUMBER());
                        }
                        break;
                    default:
                        System.out.println(ErrorMessageHandler.getFIELDERROR());
                        break;
                }
                System.out.println(product);
                System.out.println("prod update: ok");
            }
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


