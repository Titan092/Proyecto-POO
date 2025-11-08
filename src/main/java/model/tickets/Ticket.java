package model.tickets;

import exceptionHandler.ErrorMessageHandler;
import model.products.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Ticket {

    private static final DateTimeFormatter opening = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm-");
    private static final DateTimeFormatter closing = DateTimeFormatter.ofPattern("-yy-MM-dd-HH:mm");
    private String id;
    private static final int MAX_AMOUNT = 100;
    private IProduct[] ticketItems = new IProduct[MAX_AMOUNT];
    private int numProducts = 0;
    private TicketStatus ticketStatus = TicketStatus.EMPTY;

    /**
     * Ticket constructor.
     */
    public Ticket() {
        String openingTimestamp = LocalDateTime.now().format(opening);
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999+1);
        this.id = openingTimestamp + randomNumber;
    }

    /**
     * Resets the ticket.
     */
    public void newTicket() {
        ticketItems = new IProduct[MAX_AMOUNT];
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
        if (ticketStatus != TicketStatus.CLOSED) {
            boolean productFound = false;
            if (id < 0){
                System.out.println(ErrorMessageHandler.getWRONGID());
            } else {
                Map<Integer, IProduct> products = productService.getProducts();
                int availableCapacity = ticketItems.length - numProducts;
                if (amount > availableCapacity) {
                    System.out.println(ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products");
                } else {
                    for (IProduct product : products.values()) {
                        if (product != null && id == product.getId()) {
                            for (int j = 0; j < amount; j++) {
                                ticketItems[numProducts] = product;
                                numProducts++;
                            }
                            productFound = true;
                            if (ticketStatus == TicketStatus.EMPTY) {
                                ticketStatus = TicketStatus.ACTIVE;
                            }
                        }
                    }
                    if (!productFound) {
                        System.out.println(ErrorMessageHandler.getPRODUCTNOTEXIST());
                    }
                }
            }
        } else {
            System.out.println(ErrorMessageHandler.getUSE_CLOSED_TICKET());
        }
    }

    /**
     * Removes from the ticket indicated product with its id passed as a parameter.
     * @param id Unique ID of the products.
     */
    public void ticketRemove(int id) {
        if (ticketStatus != TicketStatus.CLOSED) {
            boolean found = false;
            for (int i = 0; i < ticketItems.length; i++) {
                if (ticketItems[i] != null && ticketItems[i].getId() == id) {
                    ticketItems[i] = null; // Matches are set to null.
                    found = true; // Valid ID.
                    numProducts--;
                    if (numProducts == 0) {
                        ticketStatus = TicketStatus.EMPTY;
                    }
                }
            }
            if (!found) {
                System.out.println(ErrorMessageHandler.getIDNOTEXIST());
            } else {
                IProduct[] ticketItemsAux = new IProduct[MAX_AMOUNT];
                int j = 0; // Position indicator for the auxiliary array.
                for (int i = 0; i < ticketItemsAux.length ; i++) {
                    if (ticketItems[i] != null){ // Only products that are not null will be saved, i.e., reset the array by removing deleted products (null).
                        ticketItemsAux[j] = ticketItems[i];
                        j++;
                    }
                }
                ticketItems = ticketItemsAux; // Copies the auxiliary array, where everything is already sorted, to the original array.
            }
        } else {
            System.out.println(ErrorMessageHandler.getUSE_CLOSED_TICKET());
        }
    }

    /**
     * Prints a ticket.
     */
    public void printTicket() {
        if (ticketStatus != TicketStatus.EMPTY) {
            float totalPrice = 0;
            float totalDiscount = discount(ticketItems);
            for (int i = 0; i < numProducts; i++) {
                totalPrice += ticketItems[i].getPrice();
            }
            float finalPrice = totalPrice - totalDiscount;
            System.out.printf("Total price: %.2f \n" , totalPrice);
            System.out.printf("Total discount: %.2f \n" , totalDiscount);
            System.out.printf("Final price: %.2f \n" , finalPrice);
            System.out.println("ticket print: ok");
            if (ticketStatus == TicketStatus.ACTIVE) {
                ticketStatus = TicketStatus.CLOSED;
            }
        } else {
            System.out.println(ErrorMessageHandler.getPRINT_EMPTY_TICKET());
        }
    }

    /**
     * Calculates discount of all the products given.
     * <p>
     * If there are 2 or more products of the same category, this function applies a discount only for said category.
     * @param ticketItems Products to check for discount.
     * @return Total discount.
     */
    private float discount(IProduct[] ticketItems) {
        float totalDiscount = 0f;
        int counterStationery = 0;
        int counterClothes = 0;
        int counterBook = 0;
        int counterElectronics = 0;
        for (int i = 0; i < numProducts; i++) {
            if (ticketItems[i] instanceof ICategorizable product) {
                switch (product.getCategory()) {
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
        }
        for (int j = 0; j < numProducts; j++) {
            float discount = 0f;
            float price;
            if (ticketItems[j] instanceof ICategorizable product) {
                Category category = product.getCategory();
                switch (category) {
                    case BOOK:
                        if (counterBook >= 2) {
                            price = ticketItems[j].getPrice();
                            discount = (category.getDiscount()*price);
                            System.out.print(ticketItems[j].toString() + "**discount -");
                            System.out.printf("%.2f \n",discount);
                        } else {
                            System.out.println(ticketItems[j].toString());
                        }
                        break;
                    case CLOTHES:
                        if (counterClothes >= 2) {
                            price = ticketItems[j].getPrice();
                            discount = (category.getDiscount()*price);
                            System.out.print(ticketItems[j].toString() + "**discount -");
                            System.out.printf("%.2f \n",discount);

                        } else {
                            System.out.println(ticketItems[j].toString());
                        }
                        break;
                    case STATIONERY:
                        if (counterStationery >= 2) {
                            price = ticketItems[j].getPrice();
                            discount = (category.getDiscount()*price);
                            System.out.print(ticketItems[j].toString() + "**discount -");
                            System.out.printf("%.2f \n",discount);
                        } else {
                            System.out.println(ticketItems[j].toString());
                        }
                        break;
                    case ELECTRONICS:
                        if (counterElectronics >= 2) {
                            price = ticketItems[j].getPrice();
                            discount = (category.getDiscount()*price);
                            System.out.print(ticketItems[j].toString() + "**discount -");
                            System.out.printf("%.2f \n",discount);
                        } else {
                            System.out.println(ticketItems[j].toString());
                        }
                        break;
                }
                totalDiscount += discount;
            }
        }
        return totalDiscount;
    }
}
