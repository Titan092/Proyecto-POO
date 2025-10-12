package service;

import exceptionHandler.ErrorMessageHandler;
import model.Product;

public class Ticket {

    private Product[] ticketItems;
    private final int MAX_AMOUNT = 100;
    private int numProducts;

    /**
     * Ticket constructor.
     */
    public Ticket() {
        ticketItems = new Product[MAX_AMOUNT];
        this.numProducts = 0;
    }

    /**
     * Resets the ticket.
     */
    public void newTicket() {
        ticketItems = new Product[MAX_AMOUNT];
        this.numProducts = 0;
        System.out.println("ticket new: ok");
    }

    /**
     * Adds an amount of the indicated product with its id passed as a parameter.
     * @param id Unique ID of the products.
     * @param amount Amount of products to add.
     * @param productService Array where products are located.
     */
    public void addProductToTicket(int id, int amount, ProductService productService) {
        boolean productFound = false;
        if (id < 0){
            System.out.println(ErrorMessageHandler.getWRONGID());
        } else {
            Product[] products = productService.getProducts();
            int availableCapacity = ticketItems.length - numProducts;
            if (amount > availableCapacity) {
                System.out.println(ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products");
            } else {
                for (int i = 0; i < products.length; i++) {
                    if (products[i] != null && products[i].getId() == id) {
                        for (int j = 0; j < amount; j++) {
                            ticketItems[numProducts] = products[i];
                            numProducts++;
                        }
                        productFound = true;
                    }
                }
                if (!productFound) {
                    System.out.println(ErrorMessageHandler.getPRODUCTNOTEXIST());
                }
            }
        }
    }

    /**
     * Removes from the ticket indicated product with its id passed as a parameter.
     * @param id Unique ID of the products.
     */
    public void ticketRemove(int id) {
        boolean found = false;
        for (int i = 0; i < ticketItems.length; i++) {
            if (ticketItems[i] != null && ticketItems[i].getId() == id) {
                ticketItems[i] = null; // Matches are set to null.
                found = true; // Valid ID.
                numProducts--;
            }
        }
        if (!found) {
            System.out.println(ErrorMessageHandler.getIDNOTEXIST());
        } else {
            Product[] ticketItemsAux = new Product[MAX_AMOUNT];
            int j = 0; // Position indicator for the auxiliary array.
            for (int i = 0; i < ticketItemsAux.length ; i++) {
                if (ticketItems[i] != null){ // Only products that are not null will be saved, i.e., reset the array by removing deleted products (null).
                    ticketItemsAux[j] = ticketItems[i];
                    j++;
                }
            }
            ticketItems = ticketItemsAux; // Copies the auxiliary array, where everything is already sorted, to the original array.
        }
    }

    /**
     * Prints a ticket.
     */
    public void printTicket() {
        float totalPrice = 0;
        float totalDiscount = discount(ticketItems);
        for (int i = 0; i < numProducts; i++) {
            totalPrice += ticketItems[i].getPrice();
        }
        float finalPrice = totalPrice - totalDiscount;
        System.out.println("Total price: " + totalPrice);
        System.out.println("Total discount: " + totalDiscount);
        System.out.println("Final price: " + finalPrice);
        System.out.println("ticket print: ok");
    }

    /**
     * Calculates discount of all the products given.
     * <p>
     * If there are 2 or more products of the same category, this function applies a discount only for said category.
     * @param ticketItems Products to check for discount.
     * @return Total discount.
     */
    private float discount(Product[] ticketItems) {
        float totalDiscount = 0f;
        int counterStationery = 0;
        int counterClothes = 0;
        int counterBook = 0;
        int counterElectronics = 0;
        for (int i = 0; i < numProducts; i++) {
            switch (ticketItems[i].getCategory()) {
                case BOOK:
                    counterBook++;
                    break;
                case CLOTHES:
                    counterClothes++;
                    break;
                case STATIONERY:
                    counterStationery++;
                    break;
                case ELECTRONICS:
                    counterElectronics++;
                    break;
            }
        }
        for (int j = 0; j < numProducts; j++) {
            float discount = 0f;
            switch (ticketItems[j].getCategory()) {
                case BOOK:
                    if (counterBook >= 2) {
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString() + "**discount -" + discount);
                    } else {
                        System.out.println(ticketItems[j].toString());
                    }
                    break;
                case CLOTHES:
                    if (counterClothes >= 2) {
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString() + "**discount -" + discount);
                    } else {
                        System.out.println(ticketItems[j].toString());
                    }
                    break;
                case STATIONERY:
                    if (counterStationery >= 2) {
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString() + "**discount -" + discount);
                    } else {
                        System.out.println(ticketItems[j].toString());
                    }
                    break;
                case ELECTRONICS:
                    if (counterElectronics >= 2) {
                        discount = discountAux(ticketItems[j]);
                        System.out.println(ticketItems[j].toString() + "**discount -" + discount);
                    } else {
                        System.out.println(ticketItems[j].toString());
                    }
                    break;
            }
            totalDiscount += discount;
        }
        return totalDiscount;
    }

    /**
     * Calculates de discount of an individual product given its category.
     * @param product Product to calculate the discount.
     * @return Individual discount.
     */
    private float discountAux(Product product) {
        float precio = product.getPrice();
        switch (product.getCategory()) {
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
