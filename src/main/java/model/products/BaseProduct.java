package model.products;

/**
 * Class representing a base product.
 * It extends the Product class and implements the ICategorizable interface.
 */
public class BaseProduct extends Product implements ICategorizable{
    private Category category;

    /**
     * Constructor for BaseProduct.
     * @param id Product ID.
     * @param name Product name.
     * @param category Product category.
     * @param price Product price.
     */
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
    /**
     * String representation of the BaseProduct.
     * format: {class:Product, id:ID, name:'NAME', category:CATEGORY, price:PRICE}
     * @return String representation.
     */
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return "{class:Product, id:" + super.getId() + ", name:'" + name + "'" + ", category:" + category + ", price:" + super.getPrice() + "}";
    }
}
