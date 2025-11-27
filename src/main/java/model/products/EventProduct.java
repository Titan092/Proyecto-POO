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

    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return "{class:" + this.getClass().getSimpleName() + ", id:" + super.getId() + ", name:" + "'" + name + "'" + ", price:" + super.getPrice() + ", date of Event:" + this.date + ", max people allowed:" + this.maxPeople + "}";
    }

}
