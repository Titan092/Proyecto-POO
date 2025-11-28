package model.tickets;

import exceptionHandler.ErrorMessageHandler;
import model.products.*;

import java.util.*;

public class Ticket {
    private String id;
    private static final int MAX_AMOUNT = 100;
    private ArrayList<IProduct> ticketItems = new ArrayList<>();
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
     * Adds an amount of the indicated product with its id passed as a parameter.
     * @param productID Unique ID of the products.
     * @param amount Amount of products to add.
     * @param productService Array where products are located.
     */
    public String addProductToTicket(int productID, int amount, ProductService productService) {
        String message;
        if (productID < 0){
            message = ErrorMessageHandler.getWRONGID();
        } else {
            Map<Integer, IProduct> products = productService.getProducts();
            int availableCapacity = MAX_AMOUNT - products.size();
            if (amount > availableCapacity) {
                message = ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products";
            } else {
                if (products.containsKey(productID)) {
                    IProduct product = products.get(productID);
                    if (product instanceof EventProduct eventProduct) {
                        eventProduct.setActualPeople(amount);
                        ticketItems.add(eventProduct);
                        numProducts++;
                    } else {
                        for (int i = 0; i < amount; i++) {
                            ticketItems.add(product);
                            numProducts++;
                        }
                    }
                    ticketItems.sort(Comparator.comparing(IProduct::getName));
                    if (status == TicketStatus.EMPTY) {
                        status = TicketStatus.OPEN;
                    }
                    message = "print";
                } else {
                    message = ErrorMessageHandler.getPRODUCTNOTEXIST();
                }
            }
        }
        return message;
    }

    public String addProductToTicket(int productID, int amount, String[] personalizableTexts, ProductService productService) {
        String message;
        if (productID < 0){
            message = ErrorMessageHandler.getWRONGID();
        } else {
            Map<Integer, IProduct> products = productService.getProducts();
            int availableCapacity = MAX_AMOUNT - products.size();
            if (amount > availableCapacity) {
                message = ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products";
            } else {
                if (products.containsKey(productID)) {
                    CustomProduct product = (CustomProduct) products.get(productID);
                    CustomProduct newProduct = new CustomProduct(product, personalizableTexts);
                    for (int i = 0; i < amount; i++) {
                        ticketItems.add(newProduct);
                        numProducts++;
                    }
                    ticketItems.sort(Comparator.comparing(IProduct::getName));
                    if (status == TicketStatus.EMPTY) {
                        status = TicketStatus.OPEN;
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
        Iterator<IProduct> iterator = ticketItems.iterator();
        while (iterator.hasNext()) {
            IProduct product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
                numProducts--;
                if (numProducts == 0) {
                    status = TicketStatus.EMPTY;
                }
            }
        }
        ticketItems.sort(Comparator.comparing(IProduct::getName));
    }

    public String printTicket() {
        StringBuilder string = new StringBuilder();
        if (status != TicketStatus.EMPTY) {
            int counterStationery = 0;
            int counterClothes = 0;
            int counterBook = 0;
            int counterElectronics = 0;
            // Check if we have enough products to apply a category
            for (IProduct product : ticketItems) {
                if (product instanceof BaseProduct categorizableProduct) {
                    switch (categorizableProduct.getCategory()) {
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
            for (IProduct product : ticketItems) {
                totalPrice += product.getPrice();
                string.append("  ").append(product);
                if (product instanceof BaseProduct categorizableProduct) {
                    boolean applyDiscount = false;
                    switch (categorizableProduct.getCategory()) {
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
                        float productDiscount = categorizableProduct.getPrice() * categorizableProduct.getCategory().getDiscount();
                        totalDiscount += productDiscount;
                        string.append(String.format(" **discount -%.1f%n", productDiscount));
                    } else {
                        string.append("\n");
                    }
                } else {
                    string.append("\n");
                }
            }
            float finalPrice = totalPrice - totalDiscount;
            string.append("  ").append(String.format("Total price: %.1f%n", totalPrice));
            string.append("  ").append(String.format("Total discount: %.1f%n", totalDiscount));
            string.append("  ").append(String.format("Final Price: %.1f%n", finalPrice));
        } else {
            string.append(ErrorMessageHandler.getPRINT_EMPTY_TICKET());
        }
        return string.toString();
    }

    /**
     * Prints a ticket and calculates the discount of all the products given.
     * <p>
     * If there are 2 or more products of the same category, this function applies a discount only for said category.
     * @return Ticket String.
     */
//    public String printTicket() {
//        StringBuffer sb = new StringBuffer();
//        ArrayList<String> nameAndStringFormat = new ArrayList<>();
//        if (status != TicketStatus.EMPTY) {
//            int counterStationery = 0;
//            int counterClothes = 0;
//            int counterBook = 0;
//            int counterElectronics = 0;
//            for (int i = 0; i < numProducts; i++) {
//                if (ticketItems[i] instanceof ICategorizable product) {
//                    switch (product.getCategory()) {
//                        case BOOK:
//                            counterBook++;
//                            break;
//                        case CLOTHES:
//                            counterClothes++;
//                            break;
//                        case STATIONERY:
//                            counterStationery++;
//                            break;
//                        case ELECTRONICS:
//                            counterElectronics++;
//                            break;
//                    }
//                }
//            }
//            float totalPrice = 0f;
//            float totalDiscount = 0f;
//            int actualPeople = 0;
//            for (int i = 0; i < numProducts; i++) {
//                IProduct item = ticketItems[i];
//                totalPrice += item.getPrice();
//                if (item instanceof ICategorizable product) {
//                    Category category = product.getCategory();
//                    boolean applyDiscount = false;
//                    switch (category) {
//                        case BOOK:
//                            if (counterBook >= 2) applyDiscount = true;
//                            break;
//                        case CLOTHES:
//                            if (counterClothes >= 2) applyDiscount = true;
//                            break;
//                        case STATIONERY:
//                            if (counterStationery >= 2) applyDiscount = true;
//                            break;
//                        case ELECTRONICS:
//                            if (counterElectronics >= 2) applyDiscount = true;
//                            break;
//                    }
//                    if (applyDiscount) {
//                        float itemDiscount = item.getPrice() * category.getDiscount();
//                        totalDiscount += itemDiscount;
//                        //we use the toString method to force the name to replace the " " to ' '
//                        String name = item.toString();
//                        //format: --- 'NAME' --- -> so the clean name is the field 1 splitting by '
//                        String [] cleanName = name.split("\'");
//                        nameAndStringFormat.add(cleanName[1] + "\t" + "\t" + item.toString() + String.format(" **discount -%.2f \n", itemDiscount));
//                    } else {
//                        //we use the toString method to force the name to replace the " " to ' '
//                        String name = item.toString();
//                        //format: --- 'NAME' --- -> so the clean name is the field 1 splitting by '
//                        String [] cleanName = name.split("\'");
//                        nameAndStringFormat.add(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                    }
//                } else {
//                    //we use the toString method to force the name to replace the " " to ' '
//                    String name = item.toString();
//                    //format: --- 'NAME' --- -> so the clean name is the field 1 splitting by '
//                    String [] cleanName = name.split("\'");
//                    if (!nameAndStringFormat.contains(cleanName[1] + "\t" + "\t" + item.toString()+"\n")){
//                        if (item instanceof Food){
//                            actualPeople++;
//                        } else if (item instanceof Meeting) {
//                            actualPeople++;
//                        }
//                        if (item != ticketItems[i+1]){ //the next one is not the same product
//                            if (item instanceof Food){
//                                ((Food) item).setActualPeople(actualPeople); //first time
//                                nameAndStringFormat.add(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                            } else if (item instanceof Meeting) {
//                                ((Meeting) item).setActualPeople(actualPeople); //first time
//                                nameAndStringFormat.add(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                            }
//                        }
//                    }else{
//                        if (item != ticketItems[i+1]){
//                            if (item instanceof Food){
//                                actualPeople++;
//                                nameAndStringFormat.remove(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                                ((Food) item).setActualPeople(actualPeople);
//                                nameAndStringFormat.add(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                                actualPeople = 0; //reset variable
//                            } else if (item instanceof Meeting) {
//                                actualPeople++;
//                                nameAndStringFormat.remove(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                                ((Meeting) item).setActualPeople(actualPeople);
//                                nameAndStringFormat.add(cleanName[1] + "\t" + "\t" + item.toString()+"\n");
//                                actualPeople = 0; //reset variable
//                            }
//                        }else{
//                            if (item instanceof Food){
//                                actualPeople++;
//                            } else if (item instanceof Meeting) {
//                                actualPeople++;
//                            }
//                        }
//                        if (item instanceof Food){
//                            ((Food) item).setActualPeople(((Food) item).getActualPeople()+1); //increments 1
//                        } else if (item instanceof Meeting) {
//                            ((Meeting) item).setActualPeople(((Meeting) item).getActualPeople()+1); //increments 1
//                        }
//                    }
//                }
//            }
//            Collections.sort(nameAndStringFormat);
//            for (String line : nameAndStringFormat) {
//                int tabIndex = line.indexOf('\t');
//                sb.append(line.substring(tabIndex + 1));
//            }
//
//
//            float finalPrice = totalPrice - totalDiscount;
//            sb.append("\t" + String.format("Total price: %.2f \n", totalPrice));
//            sb.append("\t" + String.format("Total discount: %.2f \n", totalDiscount));
//            sb.append("\t" + String.format("Final Price: %.2f \n", finalPrice));
//        } else {
//            sb.append(ErrorMessageHandler.getPRINT_EMPTY_TICKET());
//        }
//        return sb.toString();
//    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
