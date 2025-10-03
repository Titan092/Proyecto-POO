package service;

import model.Category;
import model.Product;

public class ProductService {
    private Product[] products;
    private final int MAX_CANTIDAD = 200;
    private int numProductos;

    public ProductService() {
        this.products = new Product[MAX_CANTIDAD];
        this.numProductos = 0;
    }

    //Funciona, pero si queremos dejarlo mas bonito y que no recorra 200 veces el array todo el rato, pues tocara cambiarlo
    public void productList(){
        System.out.println("Catalog:");
        for (int i=0; i<products.length; i++){
            if (products[i] != null){
                System.out.println(products[i].toString());
            }
        }
    }

    public void prodAdd (int id, String name, Category category, float price){ //Hay que ver como cuadrarlo con el COmmadnHandler
        boolean introducido = false;
        int i = 0;
        while (!introducido && i < MAX_CANTIDAD) {
            if (products[i] != null && products[i].getId() == id) {
                System.out.println("Error, la id introducida ya existe para otro objeto");
                introducido = true;
            }
            if (products[i] == null) {
                Product product = new Product(id, name, category, price);
                products[i] = product;
                introducido = true;
            }
            i++;
        }
    }

    public void productRemove (int id) {
        if (id < 0) {
            System.out.println("Error, la id introducida no es válida");
        } else {
            boolean removed = false;
            int i = 0;
            while (!removed && i < MAX_CANTIDAD) {
                if (products[i] != null && products[i].getId() == id) {
                    products[i] = null;
                    removed = true;
                }
                i++;
            }
            if (removed) {
                for (int j = 1; j < MAX_CANTIDAD; j++) {
                    products[j - 1] = products[j];
                }
                products[MAX_CANTIDAD - 1] = null;
                System.out.println("prod remove: ok");
            } else {
                System.out.println("Error, la id introducida no es válida");
            }
        }
    }

    public void productUpdate (int id, String field, String value){
        if (products[id] == null || id<0 || id>=products.length) {
            System.out.println("Error, la id introducida no es válida");
        }else{
            switch (field.toUpperCase()){
                case "NAME":
                    products[id].setName(value);
                    break;
                case "CATEGORY":
                    try {
                        Category categoryNew = Category.valueOf(value.toUpperCase());
                        products[id].setCategory(categoryNew);
                        System.out.println("Categoría actualizada correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Categoría no válida. Valores permitidos: "+Category.values());
                    }
                    break;
                case "PRICE":
                    float PriceValue = Float.parseFloat(value);
                    products[id].setPrice(PriceValue);
                    break;
                default:
                    System.out.println("Error al introducir el campo a actualizar");
                    break;
            }
            System.out.println(products[id].toString());
            System.out.println("prod update: ok");
        }
    }

    public Product[] getProducts() {
        return products;
    }
}
