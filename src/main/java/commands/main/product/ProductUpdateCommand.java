package commands.main.product;

import commands.Command;
import model.products.ProductService;

public class ProductUpdateCommand extends Command {
    private ProductService productService;
    public ProductUpdateCommand(ProductService productService) {
        super("update");
        this.productService = productService;
    }
}
