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

}
