package model.products;

import java.time.LocalDate;
import java.util.Date;

public class Meeting extends EventProduct {

    public Meeting(int id, String name, float price, LocalDate date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }


}
