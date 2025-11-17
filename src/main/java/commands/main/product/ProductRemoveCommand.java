package commands.main.product;

import commands.Command;
import model.products.Category;
import model.products.ProductService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductRemoveCommand extends Command {
    private ProductService productService;
    public ProductRemoveCommand(ProductService productService) {
        super("remove");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length > 0 && args[1].equals(this.getName())) {
            Pattern p=Pattern.compile("^prod remove (\\d+)$");
            Matcher m=p.matcher(String.join(" ", args));
            if(m.matches()){
                int id = Integer.parseInt(m.group(1));
                this.setMessage(productService.prodRemove(id));
                result=true;
            }
        }
        return result;
    }
}
