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

    public void ProductList(){
        System.out.println("Catalog:");
        for (int i=0; i<products.length; i++){
            System.out.println(products[i].toString());
        }
    }

    public void prodAdd (int id, String name, Category category, float price){
        if (products[id] != null){
            System.out.println("Error, la id introducida ya existe para otro objeto");
        }else{
            Product product = new Product(id,name,category,price);
            products[id] = product;
        }
    }
}
