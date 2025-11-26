package commands.main.product;

import commands.Command;
import model.products.ProductService;


import java.util.ArrayList;
import java.util.List;
/**
 * Command to handle product-related operations.
 * It is a root command that delegates to sub-commands.
 * Needs ProductService to perform product operations.
 */
public class ProductCommand extends Command {
    private List<Command> subCommands;
    private ProductService productService;
    public ProductCommand(ProductService productService) {
        super("prod");
        this.productService=productService;
        initSubCommands();
    }

    public void initSubCommands() {
        // Here we would initialize the list of sub-commands available under the product command
        subCommands=new ArrayList<>();
        subCommands.add(new ProductAddCommand(productService));
        subCommands.add(new ProductListCommand(productService));
        subCommands.add(new ProductRemoveCommand(productService));
        subCommands.add(new ProductUpdateCommand(productService));
        subCommands.add(new ProductAddFoodCommand(productService));
        subCommands.add(new ProductAddMeetingCommand(productService));
    }

    public boolean apply(String[] args) {
        boolean found=false;
        if(args[0].equals(this.getName())) {
            for (Command cmd:subCommands) {
                found=cmd.apply(args);
                if(found) {
                    this.setMessage(cmd.getMessage());
                    break;
                }
            }
        }

        return found;
    }
}
