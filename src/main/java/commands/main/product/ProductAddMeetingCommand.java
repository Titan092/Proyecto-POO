package commands.main.product;

import commands.Command;
import model.products.ProductService;

public class ProductAddMeetingCommand extends Command {
    private ProductService productService;
    public ProductAddMeetingCommand() {
        super("add_meeting");
        this.productService = productService;
    }
}
