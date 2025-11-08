package model.products;

public interface IProduct {

    int getId();

    float getPrice();

    String getName();

    void setId(int id);

    void setPrice(float price);

    void setName(String name);
}
