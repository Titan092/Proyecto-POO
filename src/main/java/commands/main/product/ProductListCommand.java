package commands.main.product;
import commands.Command;
import model.products.ProductService;


/**
 * Command to list all products.
 * Usage: prod list
 * Needs ProductService to perform the listing.
 */
public class ProductListCommand extends Command {
    private ProductService productService;
    public ProductListCommand(ProductService productService) {
        super("list");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length == 2 && args[1].equals(this.getName())) {
            this.setMessage(productService.prodList());
            result=true;
        }
        return result;
    }
}
