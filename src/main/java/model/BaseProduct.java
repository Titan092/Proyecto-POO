package model;

public class BaseProduct extends Product{
    private Category  category;
    public BaseProduct(int id, String name, float price, Category category) {
        super(id, name, price);
        this.category = category;
    }



}
