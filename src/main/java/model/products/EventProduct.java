package model.products;
import java.time.LocalDate;


public abstract class EventProduct extends Product {

    private LocalDate date;
    private int maxPeople;

    public EventProduct(int id, String name, float price, LocalDate date, int maxPeople){
        super(id,name,price);
        this.date = date;
        this.maxPeople = maxPeople;
    }

    LocalDate getDate(){
        return date;
    }

    int getMaxPeople(){
        return maxPeople;
    }

    void setDate(LocalDate date){
        this.date = date;
    }

    void setMaxPeople(int maxPeople){
        this.maxPeople = maxPeople;
    }


}
