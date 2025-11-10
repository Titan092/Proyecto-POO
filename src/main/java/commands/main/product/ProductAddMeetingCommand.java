package commands.main.product;

import commands.Command;
import model.products.ProductService;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductAddMeetingCommand extends Command {
    private ProductService productService;
    public ProductAddMeetingCommand(ProductService productService) {
        super("addMeeting");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length > 0 && args[1].equals(this.getName())) {
            Pattern p=Pattern.compile("^prod addMeeting (?: (\\d+))? \"([^\"]+)\" (\\d.) (\\d{4}-\\d{2}-\\d{2}) (\\d+)$");
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
                if(callWithId) productService.prodAddMeeting(id, name, price, expirationDate, maxPers);
                else productService.prodAddMeeting(name, price, expirationDate, maxPers);
                result=true;
            }
        }
        return result;
    }


}
