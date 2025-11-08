package model.products;

public class CustomProduct extends BaseProduct {

    private int maxPers;

    public CustomProduct(int id, String name, Category category, float price, int maxPers){
        super(id,name,category,price);
        this.maxPers = maxPers;
    }

    int getMaxPers(){
        return maxPers;
    }

    void setMaxPers(int maxPers){
        this.maxPers = maxPers;
    }


}
