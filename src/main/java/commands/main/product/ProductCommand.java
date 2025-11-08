package commands.main.product;

import commands.Command;
import model.products.ProductServiceAntiguo;

import java.util.ArrayList;
import java.util.List;

public class ProductCommand extends Command {
    private List<Command> subCommands;
    private ProductServiceAntiguo productService;
    public ProductCommand(ProductServiceAntiguo productService) {
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
        // Implementation of the product command logic goes here
        boolean found=false;
        for (Command cmd:subCommands) {
            found=cmd.apply(args);
        }
        return found;
    }
}
