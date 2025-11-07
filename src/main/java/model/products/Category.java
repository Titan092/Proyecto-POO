package model.products;

public enum Category {
    MERCH(0f),
    STATIONERY(0.05f),
    CLOTHES(0.07f),
    BOOK(0.10f),
    ELECTRONICS(0.03f);


    private final float discount;


    Category(float discount){
        this.discount = discount;
    }

    public float getDiscount(){
        return discount;
    }
}
