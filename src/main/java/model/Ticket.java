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

    public void addProductToTicket(int id, int amount, ProductService productService) {
        Product[] products = productService.getProducts();
        for (int i=products.length; i<amount; i++){ //Comienza desde la ultima posicion no ocupada y va rellenando el numero de posiciones indicados en la cantidad
            ticketItems[i] = products[id];
        }
    }

    public void printTicket(float discount){ //Hay que ver como hacer lo de los descuentos
        for (int i=0; i<ticketItems.length; i++){
            System.out.println(ticketItems[i].toString()+"**discount -"+discount);
        }
    }
}
