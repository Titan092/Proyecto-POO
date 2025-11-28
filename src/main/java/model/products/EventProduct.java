package model.products;
import java.time.LocalDate;

/**
 * Abstract class representing an event product.
 * It extends the Product class and includes additional attributes for events.
 */
public abstract class EventProduct extends Product {

    private LocalDate date;
    private int maxPeople;
    private int actualPeople = 0;

    /**
     * Constructor for EventProduct.
     * @param id ID of the product.
     * @param name Name of the product.
     * @param price Price of the product.
     * @param date Date of the event.
     * @param maxPeople Maximum number of people allowed for the event.
     */
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

    /**
     * String representation of the EventProduct.
     * format: {class:EventProduct, id:ID, name:'NAME', price:PRICE, date of Event:DATE, max people allowed:MAXPEOPLE, actual people in event:ACTUALPEOPLE}
     * or if actualPeople is 0:
     * format: {class:EventProduct, id:ID, name:'NAME', price:PRICE, date of Event:DATE, max people allowed:MAXPEOPLE}
     * @return String representation.
     */
    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        if (actualPeople == 0){
            return "{class:" + this.getClass().getSimpleName() + ", id:" + super.getId() + ", name:" + "'" + name + "'" + ", price:" + super.getPrice() + ", date of Event:" + this.date + ", max people allowed:" + this.maxPeople + "}";
        }else{
            float totalPrice = actualPeople*super.getPrice();
            return "{class:" + this.getClass().getSimpleName() + ", id:" + super.getId() + ", name:" + "'" + name + "'" + ", price:" + totalPrice + ", date of Event:" + this.date + ", max people allowed:" + this.maxPeople + ", actual people in event:" + actualPeople + "}";
        }

    }

    public void setActualPeople(int actualPeople){
        this.actualPeople = actualPeople;
    }

    public int getActualPeople() {
        return actualPeople;
    }
}
