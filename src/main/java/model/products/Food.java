package model.products;

import java.time.LocalDate;
/**
 * Class representing a food event product.
 * It extends the EventProduct class.
 */
public class Food extends EventProduct {
    /** Constructor for Food.
     * @param id ID of the product.
     * @param name Name of the product.
     * @param price Price of the product.
     * @param date Date of the event.
     * @param maxPeople Maximum number of people allowed for the event.
     */
    public Food(int id, String name, float price, LocalDate date, int maxPeople){
        super(id,name,price,date,maxPeople);
    }
    /**
     * String representation of the Food.
     * format: {class:Food, id:ID, name:'NAME', price:PRICE, date of Event:DATE, max people allowed:MAXPEOPLE, actual people in event:ACTUALPEOPLE}
     * or if actualPeople is 0:
     * format: {class:Food, id:ID, name:'NAME', price:PRICE, date of Event:DATE, max people allowed:MAXPEOPLE}
     * @return String representation.
     */
    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return super.toString();
    }
}
