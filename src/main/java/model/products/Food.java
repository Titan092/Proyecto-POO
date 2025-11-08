package model.products;

import java.util.Date;

public class Food extends EventProduct {

    public Food(int id, String name, float price, Date date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }
}
