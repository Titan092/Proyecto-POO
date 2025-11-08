package model.products;

import java.util.Date;

public class Meeting extends EventProduct {

    public Meeting(int id, String name, float price, Date date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }


}
