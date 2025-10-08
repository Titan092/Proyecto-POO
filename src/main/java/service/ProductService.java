package service;

import model.Category;
import model.Product;

public class ProductService {
    private Product[] products;
    private final int MAX_QUANTITY = 200;
    private int numProducts;

    /**
     * ProductService constructor.
     */
    public ProductService() {
        this.products = new Product[MAX_QUANTITY];
        this.numProducts = 0;
    }

    /**
     * Prints existing products.
     */
    public void productList(){
        System.out.println("Catalog:");
        for (int i = 0; i < numProducts; i++){
            if (products[i] != null){
                System.out.println(products[i].toString());
            }
        }
    }

    /**
     * Creates a product and adds it to the products service.
     * @param id Unique ID of the product.
     * @param name Name of the product.
     * @param category {@link Category} of the product.
     * @param price Positive, non-zero price of the product.
     */
    public void prodAdd(int id, String name, Category category, float price) {
        boolean end = false;
        int i = 0;
        while (!end && i < MAX_QUANTITY) {
            if (products[i] != null && products[i].getId() == id) {
                System.out.println("Error, the ID entered already exists for another object.");
                end = true;
            } else if (products[i] == null) {
                Product product = new Product(id, name, category, price);
                products[i] = product;
                numProducts++;
                end = true;
            }
            i++;
        }
        if (!end) {
            System.out.println("Error, exceeds the number of products allowed.");
        }
    }

    /**
     * Removes a product with a certain ID.
     * @param id Unique ID of the product to remove.
     */
    public void productRemove(int id) {
        if (id < 0) {
            System.out.println("Error, the ID entered is invalid.");
        } else {
            boolean removed = false;
            int i = 0;
            while (!removed && i < numProducts){
                if (products[i] != null && products[i].getId() == id) {
                    System.out.println(products[i].toString());
                    products[i] = null;
                    removed = true;
                }
                i++;
            }
            if (removed){
                for (int j = i; j < numProducts; j++){
                    products[j] = products[j + 1];
                }
                products[numProducts] = null;
                numProducts--;
                System.out.println("prod remove: ok");
            } else {
                System.out.println("The product with that ID has not been found, therefore it has not been deleted.");
            }
        }
    }

    /**
     * Updates a product's name, category or price given its ID.
     * @param id Unique ID of the product to delete.
     * @param field Field to update. Permitted fields: NAME, CATEGORY, PRICE.
     * @param value Content to update the field.
     */
    public void productUpdate(int id, String field, String value){
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
                            System.out.println("Invalid category. Permitted values: " + Category.values());
                        }
                        break;
                    case "PRICE":
                        try{
                            float priceValue = Float.parseFloat(value);
                            products[i].setPrice(priceValue);
                        } catch (NumberFormatException e) {
                            System.out.println("The price must be a valid number.");
                        }
                        break;
                    default:
                        System.out.println("Error entering the field to be updated.");
                        break;
                }
                System.out.println(products[i].toString());
                System.out.println("prod update: ok");
                found = true;
            }
            i++;
        }
        if (!found) {
            System.out.println("Error, the ID entered is invalid.");
        }
    }

    /**
     * Get the list of products.
     * @returnThe list of products.
     */
    public Product[] getProducts() {
        return products;
    }
}
