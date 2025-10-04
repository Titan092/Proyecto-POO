package model;

import exceptionHandler.ErrorMessageHandler;
import service.ProductService;

public class Ticket {

    private Product[] ticketItems;
    private final int MAXAMOUNT =100;
    private int numProd;


    /**
     *Ticket Constructor
     */
    public Ticket(){
        ticketItems=new Product[MAXAMOUNT];
        this.numProd=0;
    }


    /**
     *This method resets the ticket status
     */
    public void newTicket(){
        ticketItems=new Product[MAXAMOUNT];
        this.numProd=0;
        System.out.println("ticket new: ok");
    }

    /**
     * This method adds an amount of the indicated product with its id passed as a parameter
     * @param id
     * @param amount
     * @param productService
     */
    public void addProductToTicket(int id, int amount, ProductService productService) {
        if (id<0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        }
        //Añadir métodos de excepción (añadir excepcion si no encuentra el producto que toca añadir al ticket)
        Product[] products = productService.getProducts();
        int availableCapacity = ticketItems.length - numProd;

        //Yo dejaria asi la funcion:

        if (amount > availableCapacity){
            System.out.println("No hay espacio disponible en el ticket para la cantidad de productos que desea añadir, la capacidad que queda disponible es de: "+availableCapacity+" productos");
        }else{
            for (int i =0; i<products.length;i++){
                if (products[i] != null){
                    if (products[i].getId() == id){
                        for (int j=0;j<amount;j++){
                            ticketItems[numProd] = products[i];
                            numProd++;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param id
     */
    public void ticketRemove(int id){
        boolean found = false;
        for (int i=0; i<ticketItems.length;i++){
            if (ticketItems[i] != null) {
                if (ticketItems[i].getId() == id) {
                    ticketItems[i] = null; //Los coincidentes se ponen a null
                    found = true; //ID valida
                    numProd--;
                }
            }
        }
        if (!found){
            System.out.println("La id del producto no es válida, ya que no existe en el ticket");
        }else{
            Product [] ticketItemsAux = new Product[MAXAMOUNT];
            int j = 0; //Indicador de posicion para el array auxiliar
            for (int i=0; i<ticketItemsAux.length;i++){
                if (ticketItems[i] != null){ //Solo se guardarán los productos que no sea null, es decir reinicia el array quitando los productos borrados (null)
                    ticketItemsAux[j] = ticketItems[i];
                    j++;
                }
            }
            ticketItems = ticketItemsAux; //Copia el array auxiliar donde esta ya todo ordenado, al array original donde queda todo ordenado habiendo eliminado el producto indicado
            ticketItemsAux = null; //Liberamos espacio de memoria conviritendo el auxiliar en null
        }
    }

    /**
     *
     */
    public void printTicket(){
        float totalPrice=0, totalDiscount, finalPrice;
        totalDiscount = discount(ticketItems);
        for (int i=0; i<numProd; i++){
            totalPrice+=ticketItems[i].getPrice();
        }
        finalPrice = totalPrice-totalDiscount;
        System.out.println("Total price: "+totalPrice);
        System.out.println("Total discount: "+totalDiscount);
        System.out.println("Final Price: "+finalPrice);
        System.out.println("ticket print: ok");
    }

    /**
     * @param ticketItems
     * @return
     */
    public float discount(Product [] ticketItems){
        float discount = 0f, totalDiscount=0f;
        int contadorStationery = 0, contadorClothes = 0, contadorBook = 0, contadorElectronics = 0;
        for (int i = 0; i<numProd; i++){ //Creo que dara IndexOutOfBound pero si pongo length -1 no se si suma el ultimo descuento del ultimo producto
            switch (ticketItems[i].getCategory()){
                case BOOK:
                    contadorBook++;
                    break;
                case CLOTHES:
                    contadorClothes++;
                    break;
                case STATIONERY:
                    contadorStationery++;
                    break;
                case ELECTRONICS:
                    contadorElectronics++;
                    break;
            }
        }
        for (int j = 0; j<numProd; j++){
            discount = 0f;
            switch (ticketItems[j].getCategory()){
                case BOOK:
                    if (contadorBook>=2){
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString()+"**discount -"+discount);
                    }else
                        System.out.println(ticketItems[j].toString());
                    break;
                case CLOTHES:
                    if (contadorClothes>=2){
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString()+"**discount -"+discount);
                    }else
                        System.out.println(ticketItems[j].toString());
                    break;
                case STATIONERY:
                    if (contadorStationery>=2){
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString()+"**discount -"+discount);
                    }else
                        System.out.println(ticketItems[j].toString());
                    break;
                case ELECTRONICS:
                    if (contadorElectronics>=2){
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString()+"**discount -"+discount);
                    }else
                        System.out.println(ticketItems[j].toString());
                    break;
            }
            totalDiscount+=discount;
        }
        return totalDiscount;
    }

    /**
     * @param product
     * @return
     */
    private float discountAux(Product product){
        float precio = product.getPrice();
        switch (product.getCategory()){
            case STATIONERY:
                return precio * 0.05f;
            case CLOTHES:
                return precio * 0.07f;
            case BOOK:
                return precio * 0.10f;
            case ELECTRONICS:
                return precio * 0.03f;
            default: // MERCH y otros
                return 0f;
        }
    }
}
