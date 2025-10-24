package model;

public abstract class Product {

    private int id;
    private String name;
    private float price;

    Product(int id, String name, float price){
        this.id = id;
        this.name = name;
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
        return "{class:Product, id:"+id+", name:'"+name+", price:"+price+"}";
    }

}
