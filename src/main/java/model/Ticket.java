package model;

public class Ticket {

    private Product[] ticketItems;
    private final int MAXCANT=100;
    private int numProd;


    public Ticket(){
        ticketItems=new Product[MAXCANT];
        numProd=0;
    }

    public void addProduct(Product prod, int cant) {

    }
}
