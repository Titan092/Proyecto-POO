package commands.main.product;

import commands.Command;
import model.products.Category;
import model.products.ProductService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductAddCommand extends Command {
    private ProductService productService;
    public ProductAddCommand(ProductService productService) {
        super("add");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        if (args.length > 0 && args[1].equals(this.getName())) {
            boolean callCustom=false;
            Pattern p=Pattern.compile("^prod add (\\d+) \"([^\"]+)\" (\\S+) ([\\d.]+)(?: (\\d+))?$");
            Matcher m=p.matcher(args.toString());
            if(m.matches()){
                int id = Integer.parseInt(m.group(1));
                String name = m.group(2);
                Category category = Category.valueOf(m.group(3).toUpperCase());
                float price =Float.parseFloat(m.group(4));
                if(m.group(5)!=null) callCustom=true;
                Integer maxPers = m.group(5) != null ? Integer.parseInt(m.group(5)) : null;
                if(callCustom) productService.prodAdd(id, name, category, price, maxPers);
                else productService.prodAdd(id, name, category, price);
            }
            //productService.prodAdd();
        }
        return false;
    }
}
