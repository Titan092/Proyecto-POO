package commands.main.product;

import commands.Command;
import model.products.Category;
import model.products.ProductService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductUpdateCommand extends Command {
    private ProductService productService;
    public ProductUpdateCommand(ProductService productService) {
        super("update");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length > 0 && args[1].equals(this.getName())) {
            boolean callCustom=false;
            Pattern p=Pattern.compile("^prod update (\\d+) (NAME|CATEGORY|PRICE|name|category|price) (.+)$");
            Matcher m=p.matcher(args.toString());
            if(m.matches()){
                int id = Integer.parseInt(m.group(1));
                String field = m.group(2);
                String value = m.group(3);
                productService.prodUpdate(id, field, value);
                result=true;
            }
        }
        return result;
    }
}
