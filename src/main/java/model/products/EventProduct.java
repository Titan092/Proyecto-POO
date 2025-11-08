package model.products;
import java.util.Date;

public abstract class EventProduct extends Product {

    private Date date;
    private int maxPeople;

    public EventProduct(int id, String name, float price, Date date, int maxPeople){
        super(id,name,price);
        this.date = date;
        this.maxPeople = maxPeople;
    }

    Date getDate(){
        return date;
    }

    int getMaxPeople(){
        return maxPeople;
    }

    void setDate(Date date){
        this.date = date;
    }

    void setMaxPeople(int maxPeople){
        this.maxPeople = maxPeople;
    }


}
