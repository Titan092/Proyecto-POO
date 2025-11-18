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
        return "{class:Product, id:"+super.getId()+", name:"+ "'" +super.getName()+"'"+", category:"+category+", price:"+super.getPrice()+"}";
    }
}
