package model;

public class CustomProduct extends BaseProduct{
    private int maxTextList;

    public CustomProduct(int id, String name, float price, Category category, int maxTextList){
        super(id,name,price,category);
        this.maxTextList = maxTextList;
    }




}