package model.products;
/**
 * Interface representing a product.
 */
public interface IProduct {

    int getId();

    float getPrice();

    String getName();

    void setId(int id);

    void setPrice(float price);

    void setName(String name);
}
