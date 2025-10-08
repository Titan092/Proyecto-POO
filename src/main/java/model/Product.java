package model;

public class Product {

    private int id;
    private String name;
    private Category category;
    private float price;

    /**
     * Product contructor.
     * @param id Unique ID.
     * @param name Name of the product.
     * @param category {@link Category} of the product.
     * @param price Price of the product.
     */
    public Product(int id, String name, Category category, float price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    /**
     * Get ID.
     * @return ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Get {@link Category}.
     * @return Category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Get price.
     * @return Price.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Get name.
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set {@link Category}.
     * @param category Category.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Set name.
     * @param name Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set price.
     * @param price Price.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Set ID.
     * @param id ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the String with the requested format.
     * @return The formatted string.
     */
    @Override
    public String toString() {
        return "{class:Product, id:"+id+", name:'"+name+"', category:"+category+", price:"+price+"}";
    }

}
