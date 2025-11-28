package model.tickets;

import exceptionHandler.ErrorMessageHandler;
import model.products.*;

import java.util.*;

public class Ticket {
    private String id;
    private static final int MAX_AMOUNT = 100;
    private final ArrayList<IProduct> ticketItems = new ArrayList<>();
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

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    /**
     * Adds an amount of the indicated product with its id passed as a parameter.
     * @param productID Unique ID of the products.
     * @param amount Amount of products to add.
     * @param productService Array where products are located.
     */
    public String addProductToTicket(int productID, int amount, ProductService productService) {
        if (productID < 0) {
            return ErrorMessageHandler.getWRONGID();
        }
        if (amount <= 0) {
            return "You must add one or more products";
        }

        IProduct product = productService.getProducts().get(productID);
        if (product == null) {
            return ErrorMessageHandler.getPRODUCTNOTEXIST();
        }

        int availableCapacity = MAX_AMOUNT - ticketItems.size();
        if (amount > availableCapacity) {
            return ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products";
        }

        if (product instanceof EventProduct eventProduct) {
            if (ticketItems.contains(eventProduct)) {
                int newAmountOfPeople = eventProduct.getActualPeople() + amount;
                if (newAmountOfPeople > eventProduct.getMaxPeople()) {
                    return ErrorMessageHandler.getNOSPACETICKET() + " " + (eventProduct.getMaxPeople() - eventProduct.getActualPeople());
                }
            } else {
                eventProduct.setActualPeople(amount);
                ticketItems.add(eventProduct);
                numProducts++;
            }
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
        return "print";
    }
    /**
     * Adds an amount of the indicated custom product with its id passed as a parameter.
     * @param productID Unique ID of the products.
     * @param amount Amount of products to add.
     * @param productService Array where products are located.
     * @param personalizableTexts Array of custom names.
     */
    public String addProductToTicket(int productID, int amount, String[] personalizableTexts, ProductService productService) {
        if (productID < 0) {
            return ErrorMessageHandler.getWRONGID();
        }
        if (amount <= 0) {
            return "You must add one or more products";
        }

        int availableCapacity = MAX_AMOUNT - ticketItems.size();
        if (amount > availableCapacity) {
            return ErrorMessageHandler.getNOSPACETICKET() + availableCapacity + " products";
        }

        IProduct product = productService.getProducts().get(productID);
        if (product == null) {
            return ErrorMessageHandler.getPRODUCTNOTEXIST();
        }
        CustomProduct newProduct = new CustomProduct((CustomProduct) product, personalizableTexts);
        for (int i = 0; i < amount; i++) {
            ticketItems.add(newProduct);
            numProducts++;
        }

        ticketItems.sort(Comparator.comparing(IProduct::getName));
        if (status == TicketStatus.EMPTY) {
            status = TicketStatus.OPEN;
        }
        return "print";
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

    /**
     * Generates a formatted string representation of the ticket.
     * <p>
     * This method lists all products, calculates applicable discounts based on
     * product categories (requires 2 or more items of the same category), and
     * computes the total, discount, and final prices.
     *
     * @return a String containing the ticket details and totals, or an error
     *         message if the ticket is empty.
     */
    public String printTicket() {
        StringBuilder string = new StringBuilder();

        if (status == TicketStatus.EMPTY) {
            return string.append(ErrorMessageHandler.getPRINT_EMPTY_TICKET()).toString();
        }

        Map<Category, Integer> counts = new EnumMap<>(Category.class);
        // Check if we have enough products to apply a category
        for (IProduct product : ticketItems) {
            if (product instanceof BaseProduct categorizableProduct) {
                counts.merge(categorizableProduct.getCategory(), 1, Integer::sum);
            }
        }

        float totalPrice = 0f;
        float totalDiscount = 0f;
        for (IProduct product : ticketItems) {
            totalPrice += product.getPrice();
            string.append("  ").append(product);
            if (product instanceof BaseProduct categorizableProduct && counts.get(categorizableProduct.getCategory()) >= 2) {
                float productDiscount = categorizableProduct.getPrice() * categorizableProduct.getCategory().getDiscount();
                totalDiscount += productDiscount;
                string.append(" **discount -%.1f%n".formatted(productDiscount));
            } else {
                string.append("\n");
            }
        }

        float finalPrice = totalPrice - totalDiscount;
        string.append("  ").append("Total price: %.1f%n".formatted(totalPrice));
        string.append("  ").append("Total discount: %.1f%n".formatted(totalDiscount));
        string.append("  ").append("Final Price: %.1f%n".formatted(finalPrice));

        return string.toString();
    }
}
