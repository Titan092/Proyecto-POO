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

    public void productList(){
        System.out.println("Catalog:");
        for (int i=0; i<products.length; i++){
            System.out.println(products[i].toString());
        }
    }

    public void prodAdd (int id, String name, Category category, float price){ //Hay que ver como cuadrarlo con el COmmadnHandler
        if (products[id] != null){
            System.out.println("Error, la id introducida ya existe para otro objeto");
        }else{
            Product product = new Product(id,name,category,price);// Aquí se debería comprobar que el id y el precio son válidos (id positivo y dentro de rango, y precio positivo)
            products[id] = product;
        }
    }

    public void productRemove (int id){
        if (products[id] == null || id<0 || id>=products.length){
            System.out.println("Error, la id introducida no es válida");
        }else{
            System.out.println(products[id].toString());
            products[id] = null;
            for (int i=id+1;i<products.length;i++){
                products[i-1] = products[i];
            }
            products[products.length-1] = null; //El ultimo lo habiamos desplazado a la izquierda por lo que queda duplicado, por lo que hay que borrar este ultimo
            System.out.println("prod remove: ok");
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
