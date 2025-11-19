package model.products;

import java.time.LocalDate;
import java.util.Date;

public class Meeting extends EventProduct {

    public Meeting(int id, String name, float price, LocalDate date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }

    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return "{class:Meeting, id:"+super.getId()+", name:"+"'"+name+"'"+", price:"+super.getPrice()+", date of Event:"+ super.getDate()+", max people allowed:"+ super.getMaxPeople()+"}";
    }
}
