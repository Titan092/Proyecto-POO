package commands.main.product;

import commands.Command;
import model.products.ProductService;

public class ProductRemoveCommand extends Command {
    private ProductService productService;
    public ProductRemoveCommand(ProductService productService) {
        super("remove");
        this.productService = productService;
    }
}
