package model;

import service.ProductService;

public class Ticket {

    private Product[] ticketItems;
    private final int MAXAMOUNT =100;
    private int numProd;


    public Ticket(){
        ticketItems=new Product[MAXAMOUNT];
        numProd=0;
    }

    public void addProductToTicket(int id, int amount) {

    }

    public void printTicket(float discount){ //Hay que ver como hacer lo de los descuentos
        for (int i=0; i<ticketItems.length; i++){
            System.out.println(ticketItems[i].toString()+"**discount -"+discount);
        }
    }
}
