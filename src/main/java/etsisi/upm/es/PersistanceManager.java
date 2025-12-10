package etsisi.upm.es;

import model.products.IProduct;
import com.fasterxml.jackson.databind.*;//Cambiar cuando se pueda al ObjectMapper solo
import model.users.IUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

public class  PersistanceManager {

    private static final String PRODUCTS_FILE_PATH = "products.json";
    private static final String USERS_FILE_PATH = "users.json";

    public static void saveProducts(HashMap<Integer, IProduct> products){
        ObjectMapper mapper=new ObjectMapper();
        List<IProduct> productList=new ArrayList<>(products.values());
        try {
            mapper.writeValue(new File(PRODUCTS_FILE_PATH), productList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void saveUsers(HashMap<String, IUser> users) {
        ObjectMapper mapper = new ObjectMapper();
        List<IUser> userList = new ArrayList<>(users.values());
        try {
            mapper.writeValue(new File(USERS_FILE_PATH), userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, IProduct> loadProducts() {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<Integer, IProduct> products = new HashMap<>();
        try {
            IProduct[] productArray = mapper.readValue(new File(PRODUCTS_FILE_PATH), IProduct[].class);
            for (IProduct product : productArray) {
                products.put(product.getId(), product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static HashMap<String, IUser> loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, IUser> users = new HashMap<>();
        try {
            IUser[] userArray = mapper.readValue(new File(USERS_FILE_PATH), IUser[].class);
            for (IUser user : userArray) {
                users.put(user.getId(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
