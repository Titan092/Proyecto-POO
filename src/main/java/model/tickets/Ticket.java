package model.tickets;

import exceptionHandler.ErrorMessageHandler;
import model.products.*;
import model.users.Cash;
import model.users.IUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ticket {
    private String id;
    private static final int MAX_AMOUNT = 100;
    private IProduct[] ticketItems = new IProduct[MAX_AMOUNT];
    private int numProducts = 0;
    private TicketStatus status = TicketStatus.EMPTY;

    /**
     * Ticket constructor.
     */
    public Ticket(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TicketStatus getStatus() {
        return status;
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
    public String addProductToTicket(int id, int amount, ProductService productService) {
        String message;
        if (id < 0){
            message = ErrorMessageHandler.getWRONGID();
        } else {
            Map<Integer, IProduct> products = productService.getProducts();
            int availableCapacity = ticketItems.length - numProducts;
            if (amount > availableCapacity) {
                message = ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products";
            } else {
                if (products.containsKey(id)) {
                    IProduct product = products.get(id);
                    for (int i = 0; i < amount; i++) {
                        ticketItems[numProducts] = product;
                        numProducts++;
                    }
                    if (status == TicketStatus.EMPTY) {
                        status = TicketStatus.ACTIVE;
                    }
                    message = "print";
                } else {
                    message = ErrorMessageHandler.getPRODUCTNOTEXIST();
                }
            }
        }
        return message;
    }

    /**
     * Removes from the ticket indicated product with its id passed as a parameter.
     * @param id Unique ID of the products.
     */
    public void ticketRemove(int id) {
        if (status != TicketStatus.CLOSED) {
            boolean found = false;
            for (int i = 0; i < ticketItems.length; i++) {
                if (ticketItems[i] != null && ticketItems[i].getId() == id) {
                    ticketItems[i] = null; // Matches are set to null.
                    found = true; // Valid ID.
                    numProducts--;
                    if (numProducts == 0) {
                        status = TicketStatus.EMPTY;
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
     * Prints a ticket and calculates the discount of all the products given.
     * <p>
     * If there are 2 or more products of the same category, this function applies a discount only for said category.
     * @return Ticket String.
     */
    public String printTicket() {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> nameAndStringFormat = new ArrayList<>();
        if (status != TicketStatus.EMPTY) {
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
            float totalPrice = 0f;
            float totalDiscount = 0f;
            for (int i = 0; i < numProducts; i++) {
                IProduct item = ticketItems[i];
                totalPrice += item.getPrice();
                if (item instanceof ICategorizable product) {
                    Category category = product.getCategory();
                    boolean applyDiscount = false;
                    switch (category) {
                        case BOOK:
                            if (counterBook >= 2) applyDiscount = true;
                            break;
                        case CLOTHES:
                            if (counterClothes >= 2) applyDiscount = true;
                            break;
                        case STATIONERY:
                            if (counterStationery >= 2) applyDiscount = true;
                            break;
                        case ELECTRONICS:
                            if (counterElectronics >= 2) applyDiscount = true;
                            break;
                    }
                    if (applyDiscount) {
                        float itemDiscount = item.getPrice() * category.getDiscount();
                        totalDiscount += itemDiscount;
                        nameAndStringFormat.add(item.getName() + "\t" + "\t" + item.toString() + String.format(" **discount -%.2f \n", itemDiscount));
                    } else {
                        nameAndStringFormat.add(item.getName() + "\t" + "\t" + item.toString() + "\n");
                    }
                } else {
                    nameAndStringFormat.add(item.getName() + "\t" + "\t" + item.toString() + "\n");
                }
            }
            Collections.sort(nameAndStringFormat);
            for (String line : nameAndStringFormat) {
                int firstSpaceIndex = line.indexOf("\t");
                if (firstSpaceIndex != -1) {
                    sb.append(line.substring(firstSpaceIndex + 1));
                }
            }
            float finalPrice = totalPrice - totalDiscount;
            sb.append("\t" + String.format("Total price: %.2f \n", totalPrice));
            sb.append("\t" + String.format("Total discount: %.2f \n", totalDiscount));
            sb.append("\t" + String.format("Final Price: %.2f \n", finalPrice));
//            if (status == TicketStatus.ACTIVE) {
//                String closingTimestamp = LocalDateTime.now().format(closing);
//                this.id += closingTimestamp;
//                status = TicketStatus.CLOSED;
//            }
        } else {
            sb.append(ErrorMessageHandler.getPRINT_EMPTY_TICKET());
        }
        return sb.toString();
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
