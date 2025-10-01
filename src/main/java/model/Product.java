package model;

public class Product {

    private int id;
    private String name;
    private Category category;
    private float price;


    public Product (int id, String name, Category category, float price){
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }


    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{class:Product, id:"+id+", name:'"+name+"', category:"+category+", price:"+price+"}";
    }

    public void prodAdd (int id, String name, Category category, float price){

    }


}
