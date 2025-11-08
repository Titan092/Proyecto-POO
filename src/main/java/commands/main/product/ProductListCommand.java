package commands.main.product;

import commands.Command;
import model.products.Category;
import model.products.ProductService;



public class ProductListCommand extends Command {
    private ProductService productService;
    public ProductListCommand(ProductService productService) {
        super("list");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length == 2 && args[1].equals(this.getName())) {
            productService.prodList();
            result=true;
        }
        return result;
    }
}
