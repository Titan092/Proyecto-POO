package model.products;

import java.time.LocalDate;
import java.util.Date;

public class Meeting extends EventProduct {

    public Meeting(int id, String name, float price, LocalDate date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }

    @Override
    public String toString() {
        return "{class:Meeting, id:"+super.getId()+", name:"+"'"+super.getName()+"'"+", price:"+super.getPrice()+", date of Event:"+ super.getDate()+", max people allowed:"+ super.getMaxPeople()+"}";
    }
}
