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

    public void ticketNew(){
        this.ticketItems = new Product[MAXAMOUNT];//Elimina el objeto y crea uno nuevo
        this.numProd = 0; //Reinicia el numero de productos
        System.out.println("ticket new: ok");
    }

    /*
    public void ticketRemoveTest(int id){
        for (int i=0; i<ticketItems.length;i++){
            if (ticketItems[i].getId() == id){
                ticketItems[i] = null; //Los coincidentes se ponen a null
            }
        }
        Product [] ticketItemsAux = new Product[MAXAMOUNT];
        int j = 0; //Contador de posicion para el array auxiliar
        for (int i=0; i<ticketItemsAux.length;i++){
            if (ticketItems[i] != null){ //Solo se guardarán los productos que no sea null, es decir reinicia el array quitando los productos borrados (null)
                ticketItemsAux[j] = ticketItems[i];
                j++;
            }
        }
        ticketItems = ticketItemsAux; //Copia el array auxiliar donde esta ya todo ordenado, al array original donde queda todo ordenado habiendo eliminado el producto indicado
    }

     */

    public void ticketRemove(int id){
        for (int i=0; i<ticketItems.length;i++){
            if (ticketItems[i].getId() == id){
                ticketItems[i] = null; //De momento lo dejamos a null, tendriamos que ver como hacerlo, si desplazarlo o con una lista, hashmap,etc
            }
        }
    }
}
