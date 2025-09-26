package service;

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
            int productId = products[i].getId();
            String name = products[i].get
            {"class:Product, "+}
        }
    }
}
