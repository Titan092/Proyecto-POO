package commands.main.product;

import commands.Command;
import model.products.ProductServiceAntiguo;

public class ProductAddFoodCommand extends Command {
    private ProductServiceAntiguo productService;
    public ProductAddFoodCommand(ProductServiceAntiguo productService) {
        super("addFood");
        this.productService = productService;
    }
}
