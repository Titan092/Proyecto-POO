package model.products;

import etsisi.upm.es.PersistanceManager;
import exceptionHandler.ErrorMessageHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Service class for managing products.
 */
public class ProductService {

    private HashMap<Integer,IProduct> products;
    private final int MAX_QUANTITY = 200;
    private int numProducts;

    /**
     * Constructor for ProductService.
     */
    public ProductService(){//Relacion agregación
        this.products = PersistanceManager.loadProducts();
        this.numProducts = products.size();
    }

    /**Command for baseProduct with random ID
     *
     * @param name
     * @param category
     * @param price
     * @return
     */
    public String prodAdd(String name, Category category, float price){
        String message;
        int id;
        do{
            id = ThreadLocalRandom.current().nextInt(1000000, 9999999+1);
        }while(products.containsKey(id));
        products.put(id, new BaseProduct(id, name , category, price));
        numProducts++;
        message = ((BaseProduct) products.get(id)).toString() + "\nprod add: ok\n";
        return message;
    }


    /**Command for baseProduct with explicit ID
     *
     * @param id
     * @param name
     * @param category
     * @param price
     * @return
     */
    public String prodAdd(int id, String name, Category category ,float price){
        String message = null;
        if (id<0){
            message=ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    products.put(id,new BaseProduct(id, name , category, price));
                    numProducts++;
                    message = ((BaseProduct) products.get(id)).toString() + "\nprod add: ok\n";
                }
            }else{
                message=ErrorMessageHandler.getEXISTINGID();
            }
        }
        return message;
    }

    /**Command for CustomProduct with random ID
     *
     * @param name
     * @param category
     * @param price
     * @param maxPers
     * @return
     */
    public String prodAdd(String name, Category category, float price, int maxPers){
        String message;
        int id;
        do{
            id = (int) (Math.random() * (9999999-1000000+1)) +1000000;
        }while(products.containsKey(id));
        products.put(id,new CustomProduct(id,name,category,price,maxPers, new String[]{}));
        numProducts++;
        message = ((CustomProduct) products.get(id)).toString() + "\nprod add: ok\n";
        return message;
    }

    /**Command for CustomProduct
     *
     * @param id
     * @param name
     * @param category
     * @param price
     * @param maxPers
     * @return
     */
    public String prodAdd(int id, String name, Category category, float price, int maxPers){
        String message = null;
        if (id<0){
            message=ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                if (products.size()<MAX_QUANTITY){
                    String[] personalizableTexts = new String[maxPers];
                    products.put(id,new CustomProduct(id,name,category,price,maxPers, personalizableTexts));
                    numProducts++;
                    message = ((CustomProduct) products.get(id)).toString() + "\nprod add: ok\n";
                }
            }else{
                message=ErrorMessageHandler.getEXISTINGID();
            }
        }
        return message;
    }

    /**
     * Adds a new Food product with a randomly generated 7-digit ID.
     *
     * @param name      The name of the food.
     * @param price     The price of the food.
     * @param date      The expiration or relevant date.
     * @param maxPeople The maximum number of people suggested for this food.
     * @return A status message indicating success or a specific error message.
     */
    public String prodAddFood(String name, float price, LocalDate date, int maxPeople) {
        int id;
        do {
            id = (int) (Math.random() * (9999999-1000000+1)) + 1000000; //7 digits ID
        } while (products.containsKey(id));

        return prodAddFood(id, name, price, date, maxPeople);
    }

    /**
     * Adds a new Food product with a specific ID.
     * Validates ID, max people, uniqueness, and storage capacity.
     *
     * @param id        The unique identifier for the food.
     * @param name      The name of the food.
     * @param price     The price of the food.
     * @param date      The expiration or relevant date.
     * @param maxPeople The maximum number of people suggested for this food.
     * @return A status message indicating success, or an error message if validation fails.
     */
    public String prodAddFood(int id, String name, float price, LocalDate date, int maxPeople) {
        if (id < 0) {
            return ErrorMessageHandler.getWRONGID();
        }
        if (maxPeople > 100) {
            return ErrorMessageHandler.getMaxPeopleErrorFood() + "\n";
        }
        if (products.containsKey(id)) {
            return ErrorMessageHandler.getEXISTINGID();
        }
        if (products.size() >= MAX_QUANTITY) {
            return "Can't add more products.\n";
        }
        LocalDate threshold = LocalDate.now().plusDays(3);
        if (date.isBefore(threshold)) {
            return "Foods' dates must be set three days after the date it's added.\n";
        }
        Food food = new Food(id, name, price, date, maxPeople);
        products.put(id, food);
        numProducts++;
        return food + "\nprod addFood: ok\n";
    }

    /**
     * Adds a new Meeting product with a randomly generated 7-digit ID.
     *
     * @param name      The name of the meeting.
     * @param price     The price of the meeting.
     * @param date      The expiration or relevant date.
     * @param maxPeople The maximum number of people suggested for this meeting.
     * @return A status message indicating success or a specific error message.
     */
    public String prodAddMeeting(String name, float price, LocalDate date, int maxPeople) {
        int id;
        do {
            id = (int) (Math.random() * (9999999-1000000+1)) + 1000000; //7 digits ID
        } while (products.containsKey(id));
        return prodAddMeeting(id, name, price, date, maxPeople);
    }

    /**
     * Adds a new Meeting product with a specific ID.
     * Validates ID, max people, uniqueness, and storage capacity.
     *
     * @param id        The unique identifier for the meeting.
     * @param name      The name of the meeting.
     * @param price     The price of the meeting.
     * @param date      The expiration or relevant date.
     * @param maxPeople The maximum number of people suggested for this meeting.
     * @return A status message indicating success, or an error message if validation fails.
     */
    public String prodAddMeeting(int id, String name, float price, LocalDate date, int maxPeople) {
        if (id < 0) {
            return ErrorMessageHandler.getWRONGID();
        }
        if (maxPeople > 100) {
            return ErrorMessageHandler.getMaxPeopleErrorMeeting() + "\n";
        }
        if (products.containsKey(id)) {
            return ErrorMessageHandler.getEXISTINGID();
        }
        if (products.size() >= MAX_QUANTITY) {
            return "Can't add more products.\n";
        }
        LocalDateTime meetingStartTime = date.atStartOfDay();
        LocalDateTime threshold = LocalDateTime.now().plusHours(12);
        if (meetingStartTime.isBefore(threshold)) {
            return "Meetings' dates must be set twelve hours after the date it's added.\n";
        }
        Meeting meeting = new Meeting(id, name, price, date, maxPeople);
        products.put(id, meeting);
        numProducts++;
        return meeting + "\nprod addMeeting: ok\n";
    }

    /**Command for updating a product
     *
     * @param id
     * @param field
     * @param value
     * @return
     */
    public String prodUpdate(int id, String field, String value){
        String result;
        if (id<0){
            result = ErrorMessageHandler.getWRONGID();
        }else{
            if (!products.containsKey(id)){
                result = ErrorMessageHandler.getIDNOTEXIST();
            }else{
                IProduct product = products.get(id); //the product that is going to be updated
                switch (field.toUpperCase()){
                    case "NAME":
                        product.setName(value);
                        result = product.toString() + "\nprod update: ok\n\n";
                        break;
                    case "CATEGORY":
                        try{
                            Category categoryNew = Category.valueOf(value.toUpperCase());
                            if (product instanceof ICategorizable){
                                ((ICategorizable) product).setCategory(categoryNew);
                                result = product.toString() + "\nprod update: ok\n\n";
                            }else{
                                result=("This type of product do not have category");
                            }
                        }catch (IllegalArgumentException e){
                            result=(ErrorMessageHandler.getVALIDCATEGORY());
                        }
                        break;
                    case "PRICE":
                        try{
                            float priceValue = Float.parseFloat(value);
                            product.setPrice(priceValue);
                            result=product.toString() + "\nprod update: ok\n\n";
                        }catch (NumberFormatException e) {
                            result=(ErrorMessageHandler.getVALIDNUMBER());
                        }
                        break;
                    default:
                        result=(ErrorMessageHandler.getFIELDERROR());
                        break;
                }
            }
        }
        return result;
    }
    /**Command for listing all products
     *
     * @return
     */
    public String prodList(){
        StringBuffer sb = new StringBuffer();
        sb.append("Catalog:\n");
        ArrayList<String> productsOrderedByID = new ArrayList<>();
        for (Map.Entry<Integer,IProduct> entry : products.entrySet()){
            productsOrderedByID.add(entry.getKey()+ "\t" + entry.getValue().toString()+"\n");

        }
        Collections.sort(productsOrderedByID);
        for (int i=0; i<productsOrderedByID.size();i++){
            String [] productsOrderedByIDSeparated = productsOrderedByID.get(i).split("\t");
            sb.append("  "+productsOrderedByIDSeparated[1]);
        }
        sb.append("prod list: ok");
        sb.append("\n");
        return sb.toString();
    }
    /**Command for removing a product
     *
     * @param id
     * @return
     */
    public String prodRemove(int id) {
        String message;
        if (id < 0) {
            message=ErrorMessageHandler.getWRONGID();
        } else {
            if (!products.containsKey(id)) {
                message=ErrorMessageHandler.getNOTFINDGID();
            } else {
                message = products.get(id).toString() + "\nprod remove: ok\n";
                products.remove(id);
                numProducts--;
            }
        }
        return message;
    }

    public HashMap<Integer,IProduct> getProducts(){
        return products;
    }


}


