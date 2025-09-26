package model;

public class Ticket {

    private Product[] ticketItems;
    private final int MAXAMOUNT =100;
    private int numProd;


    public Ticket(){
        ticketItems=new Product[MAXAMOUNT];
        numProd=0;
    }

    public void addProduct(Product product, int amount) {

    }
}
