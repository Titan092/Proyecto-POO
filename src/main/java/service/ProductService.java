package service;

import model.Category;
import model.Product;

public class ProductService {
    private Product[] products;
    private final int MAX_CANTIDAD = 200;
    private int numProducts;

    /**
     *ProductService Constructor
     */
    public ProductService() {
        this.products = new Product[MAX_CANTIDAD];
        this.numProducts = 0;
    }

    /**
     * Prints the Catalog of the products added
     */
    public void productList(){
        System.out.println("Catalog:");
        for (int i = 0; i<numProducts; i++){
            if (products[i] != null){
                System.out.println(products[i].toString());
            }
        }
    }

    /**
     * Creates a product and adds it to the products array
     * @param id
     * @param name
     * @param category
     * @param price
     */
    public void prodAdd (int id, String name, Category category, float price){ //Hay que ver como cuadrarlo con el COmmadnHandler
        boolean terminar = false;
        int i = 0;
        while (!terminar && i < MAX_CANTIDAD) {
            if (products[i] != null && products[i].getId() == id) {
                System.out.println("Error, la id introducida ya existe para otro objeto");
                terminar = true;
            } else if (products[i] == null) {
                Product product = new Product(id, name, category, price);
                products[i] = product;
                numProducts++;
                terminar = true;
            }
            i++;
        }
        if (!terminar) {
            System.out.println("Error, excede el numero de productos permitidos");
        }
    }

    /**
     *
     * @param id
     */
    public void productRemove (int id) {
        if (id < 0) {
            System.out.println("Error, la id introducida no es válida");
        } else {
            boolean removed = false;
            int i = 0;
            while (!removed && i <numProducts){
                if (products[i] != null && products[i].getId() == id) {
                    System.out.println(products[i].toString());
                    products[i] = null;
                    removed = true;
                }
                i++;
            }
            if (removed){
                for (int j = i; j<numProducts; j++){
                    products[j] = products[j+1];
                }
                products[numProducts] = null; //Eliminamos el final ya que esta duplicado porque hemos desplazado todos uno a la izquierda a partir del elemento borrado
                numProducts--;
                System.out.println("prod remove: ok");
            }else{
                System.out.println("No se ha encontrado el producto con esa id, por ende, no ha sido borrado");
            }
        }
    }

    /**
     *
     * @param id
     * @param field
     * @param value
     */
    public void productUpdate (int id, String field, String value){
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < MAX_CANTIDAD) {
            if (products[i] != null && products[i].getId() == id) {
                switch (field.toUpperCase()) {
                    case "NAME":
                        products[i].setName(value);
                        break;
                    case "CATEGORY":
                        try {
                            Category categoryNew = Category.valueOf(value.toUpperCase());
                            products[i].setCategory(categoryNew);
                            System.out.println("Categoría actualizada correctamente.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Categoría no válida. Valores permitidos: "+Category.values());
                        }
                        break;
                    case "PRICE":
                        float priceValue = Float.parseFloat(value);
                        products[i].setPrice(priceValue);
                        break;
                    default:
                        System.out.println("Error al introducir el campo a actualizar");
                        break;
                }
                System.out.println(products[i].toString());
                System.out.println("prod update: ok");
                encontrado = true;
            }
            i++;
        }
        if (!encontrado) {
            System.out.println("Error, la id introducida no es válida");
        }
    }

    public Product[] getProducts() {
        return products;
    }
}
