package model.products;

public class BaseProduct extends Product {
    private Category category;
    public BaseProduct(int id, String name, Category category, float price) {
        super(id, name, price);
        this.category = category;
    }

    Category getCategory(){
        return category;
    }

    void setCategory(Category category){
        this.category = category;
    }

}
