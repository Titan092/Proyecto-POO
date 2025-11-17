package model.products;

public class CustomProduct extends BaseProduct {

    private int maxPers;

    public CustomProduct(int id, String name, Category category, float price, int maxPers){
        super(id,name,category,price);
        this.maxPers = maxPers;
    }

    public int getMaxPers(){
        return maxPers;
    }

    public void setMaxPers(int maxPers){
        this.maxPers = maxPers;
    }

    @Override
    public void setCategory(Category category) {
        super.setCategory(category);
    }

    @Override
    public String toString() {
        return "{class:Product, id:"+super.getId()+", name:"+ "'" +super.getName()+"'"+", category:"+super.getCategory()+", price:"+super.getPrice()+", maxPers: "+maxPers+"}\n";
    }
}
