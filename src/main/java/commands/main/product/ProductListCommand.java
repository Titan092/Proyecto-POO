package commands.main.product;

import commands.Command;
import model.products.ProductService;

public class ProductListCommand extends Command {
    private ProductService productService;
    public ProductListCommand(ProductService productService) {
        super("list");
        this.productService = productService;
    }
}
