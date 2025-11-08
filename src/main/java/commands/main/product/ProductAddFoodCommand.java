package commands.main.product;

import commands.Command;
import model.products.ProductService;

public class ProductAddFoodCommand extends Command {
    private ProductService productService;
    public ProductAddFoodCommand(ProductService productService) {
        super("addFood");
        this.productService = productService;
    }
}
