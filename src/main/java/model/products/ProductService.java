package model.products;

import exceptionHandler.ErrorMessageHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ProductService {

    private HashMap<Integer,IProduct> products;
    private final int MAX_QUANTITY = 200;
    private int numProducts;

    public ProductService(){//Relacion agregación
        this.products = new HashMap<>();
        this.numProducts = 0;
    }

    //comand for baseProduct with random ID
    public String prodAdd(String name, Category category, float price){
        int id;
        do{
            id = ThreadLocalRandom.current().nextInt(1000000, 9999999+1);
        }while(products.containsKey(id));
        products.put(id, new BaseProduct(id, name , category, price));
        numProducts++;
        return null;
    }


    //command for baseProduct with explicit ID
    public String prodAdd(int id, String name, Category category ,float price){
        String message=null;
        if (id<0){
            message=ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new BaseProduct(id, name , category, price));
                    numProducts++;
                }
            }else{
                message=ErrorMessageHandler.getEXISTINGID();
            }
        }
        return message;
    }

    //comand for CustomProduct with random ID
    public String prodAdd(String name, Category category, float price, int maxPers){
        int id;
        do{
            id = (int) (Math.random() * (9999999-1000000+1)) +1000000;
        }while(products.containsKey(id));
        products.put(id,new CustomProduct(id,name,category,price,maxPers));
        numProducts++;
        return null;
    }

    //command for CustomProduct
    public String prodAdd(int id, String name, Category category, float price, int maxPers){
        String message=null;
        if (id<0){
            message=ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new CustomProduct(id,name,category,price,maxPers));
                    numProducts++;
                }
            }else{
                message=ErrorMessageHandler.getEXISTINGID();
            }
        }
        return message;
    }

    //commando for Food with random ID
    public String prodAddFood(String name, float price, LocalDate date, int maxPeople){
        int id;
        do{
            id = (int) (Math.random() * (9999999-1000000+1)) +1000000; //7 digits ID
        }while(products.containsKey(id));
        products.put(id,new Food(id,name,price,date,maxPeople));
        numProducts++;
        return null;
    }

    public String prodAddFood(int id, String name, float price, LocalDate date, int maxPeople){
        String message=null;
        if (id<0){
            message=ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new Food(id,name,price,date,maxPeople));
                    numProducts++;
                }
            }else{
                message=ErrorMessageHandler.getEXISTINGID();
            }
        }
        return message;
    }

    //command for Meeting with random ID
    public String prodAddMeeting(String name, float price, LocalDate date, int maxPeople){
        int id;
        do{
            id = (int) (Math.random() * (9999999-1000000+1)) +1000000; //7 digits ID
        }while(products.containsKey(id));
        products.put(id,new Meeting(id,name,price,date,maxPeople));
        numProducts++;
        return null;
    }

    public String prodAddMeeting(int id, String name, float price, LocalDate date, int maxPeople){
        String message=null;
        if (id<0){
            message=ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new Meeting(id,name,price,date,maxPeople));
                    numProducts++;
                }
            }else{
                message=ErrorMessageHandler.getEXISTINGID();
            }
        }
        return message;
    }


    public String prodUpdate(int id, String field, String value){
        String result="";
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
                result=result+product.toString();
                result=result+"prod update: ok";
            }
        }
        return result;
    }

    public String prodList(){
        StringBuffer sb = new StringBuffer();
        sb.append("Catalog:\n");
        for (Map.Entry<Integer,IProduct> entry : products.entrySet()){
            sb.append(products.get(entry.getValue()).toString());
        }
        return sb.toString();
    }

    public String prodRemove(int id) {
        String message=null;
        if (id < 0) {
            message=ErrorMessageHandler.getWRONGID();
        } else {
            if (!products.containsKey(id)) {
                message=ErrorMessageHandler.getNOTFINDGID();
            } else {
                products.remove(id);
                numProducts--;
            }
        }
        return message;
    }

    public Map<Integer,IProduct> getProducts(){
        return products;
    }
}


