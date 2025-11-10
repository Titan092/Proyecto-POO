package commands.main.product;

import commands.Command;
import model.products.Category;
import model.products.ProductService;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductAddCommand extends Command {
    private ProductService productService;
    public ProductAddCommand(ProductService productService) {
        super("add");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length > 0 && args[1].equals(this.getName())) {
            //prod add [<id>] "<name>" <category> <price> [<maxPers>]
            boolean callWithId=false;
            boolean callCustom=false;
            Pattern p=Pattern.compile("^prod add (?:(\\d+))? \"([^\"]+)\" (.+) ([\\d.]+)(?: (\\d+))?$");
            Matcher m=p.matcher(String.join(" ", args));
            if(m.matches()){
                int id=0;
                if(m.group(1)!=null){
                    callWithId=true;
                    id = Integer.parseInt(m.group(1));
                }
                String name = m.group(2);
                Category category = Category.valueOf(m.group(3).toUpperCase());
                float price =Float.parseFloat(m.group(4));
                int maxPers =0;
                if(m.group(5)!=null){
                    callCustom=true;
                    maxPers = Integer.parseInt(m.group(5) );
                }
                if(callCustom && callWithId) productService.prodAdd(id, name, category, price, maxPers);
                else if(!callCustom && callWithId) productService.prodAdd(id, name, category, price);
                else if(callCustom && !callWithId) productService.prodAdd(name, category, price, maxPers);
                else productService.prodAdd(name, category, price);
                result=true;
            }
        }
        return result;
    }
}
