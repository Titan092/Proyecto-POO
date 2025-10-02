package model;

import service.ProductService;

public class Ticket {

    private Product[] ticketItems;
    private final int MAXAMOUNT =100;
    private int numProd;


    public Ticket(){
        ticketItems=new Product[MAXAMOUNT];
        this.numProd=0;
        System.out.println("ticket new: ok");
    }

    public void addProductToTicket(int id, int amount, ProductService productService) {
        Product[] products = productService.getProducts();
        for (int i=products.length; i<amount; i++){ //Comienza desde la ultima posicion no ocupada y va rellenando el numero de posiciones indicados en la cantidad
            ticketItems[i] = products[id];
        }
    }

    public void printTicket(float discount){ //Hay que ver como hacer lo de los descuentos
        float totalPrice=0, totalDiscount, finalPrice;
        for (int i=0; i<ticketItems.length; i++){
            System.out.println(ticketItems[i].toString()+"**discount -"+discount);
            totalPrice+=ticketItems[i].getPrice();
        }
        totalDiscount = discount(ticketItems);
        finalPrice = totalPrice-totalDiscount;
        System.out.println("Total price: "+totalPrice);
        System.out.println("Total discount: "+totalDiscount);
        System.out.println("Final Price: "+finalPrice);
        System.out.println("ticket print: ok");
    }

    //He borrado ticket new ya que lo hace el constructor


    public void ticketRemove(int id){
        boolean found = false;
        for (int i=0; i<ticketItems.length;i++){
            if (ticketItems[i] != null) {
                if (ticketItems[i].getId() == id) {
                    ticketItems[i] = null; //Los coincidentes se ponen a null
                    found = true; //ID valida
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

    public float discount(Product [] ticketItems){
        float discount = 0, totalDiscount=0;
        for (int i = 0; i<ticketItems.length; i++){ //Creo que dara IndexOutOfBound pero si pongo length -1 no se si suma el ultimo descuento del ultimo producto
            if (ticketItems[i] == ticketItems[i+1]){
                discount = discountAux(ticketItems[i]);
                totalDiscount+=discount;
            }
        }
        return totalDiscount;
    }

    private float discountAux(Product product){
        float descuento=0, precio=product.getPrice();
        if (product.getCategory() == Category.STATIONERY){
            descuento = (float) (precio*(0.05));
            return descuento;
        } else if (product.getCategory() == Category.CLOTHES) {
            descuento = (float) (precio*(0*07));
            return descuento;
        } else if (product.getCategory() == Category.BOOK) {
            descuento = (float) (precio*(0*1));
            return descuento;
        } else if (product.getCategory() == Category.ELECTRONICS) {
            descuento = (float) (precio*(0.03));
            return descuento;
        }else
            return descuento; //devolvera 0 en el caso de MERCH
    }
}
