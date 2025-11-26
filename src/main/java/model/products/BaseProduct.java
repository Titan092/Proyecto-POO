package model.products;

public class BaseProduct extends Product implements ICategorizable{
    private Category category;
    public BaseProduct(int id, String name, Category category, float price) {
        super(id, name, price);
        this.category = category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return "{class:Product, id:" + super.getId() + ", name:'" + name + "'" + ", category:" + category + ", price:" + super.getPrice() + "}";
    }
}
