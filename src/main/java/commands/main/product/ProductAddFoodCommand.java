package commands.main.product;

import commands.Command;
import model.products.Category;
import model.products.ProductService;

import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductAddFoodCommand extends Command {
    private ProductService productService;
    public ProductAddFoodCommand(ProductService productService) {
        super("addFood");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length > 0 && args[1].equals(this.getName())) {
            //prod addFood [<id>] "< name>" <price> <expiration: yyyy-MM-dd> <max_people>
            Pattern p=Pattern.compile("^prod addFood (?: (\\d+))? \"([^\"]+)\" (\\d.) (\\d{4}-\\d{2}-\\d{2}) (\\d+)$");
            Matcher m=p.matcher(String.join(" ", args));
            if(m.matches()){
                boolean callWithId=false;
                int id=0;
                if(m.group(1)!=null){
                    id = Integer.parseInt(m.group(1));
                    callWithId=true;
                }
                String name = m.group(2);
                float price =Float.parseFloat(m.group(3));
                LocalDate expirationDate = LocalDate.parse(m.group(4));
                int maxPers = Integer.parseInt(m.group(5));
                if(callWithId) this.setMessage(productService.prodAddFood(id, name, price, expirationDate, maxPers));
                else this.setMessage(productService.prodAddFood(name, price, expirationDate, maxPers));
                result=true;
            }
        }
        return result;
    }
}
