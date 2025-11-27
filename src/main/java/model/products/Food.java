package model.products;

import java.time.LocalDate;

public class Food extends EventProduct {

    public Food(int id, String name, float price, LocalDate date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }

    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return "{class:Food, id:"+super.getId()+", name:"+"'"+name+"'"+", price:"+super.getPrice()+", date of Event:"+ super.getDate()+", max people allowed:"+ super.getMaxPeople()+"}";
    }
}
