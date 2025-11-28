package commands.main.product;
import commands.Command;
import model.products.ProductService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Command to update a product's field by ID.
 * Usage: prod update <id> <field> <value>
 * Needs ProductService to perform the update.
 */
public class ProductUpdateCommand extends Command {
    private ProductService productService;
    public ProductUpdateCommand(ProductService productService) {
        super("update");
        this.productService = productService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length > 0 && args[1].equals(this.getName())) {
            Pattern p=Pattern.compile("^prod update (\\d+) (NAME|CATEGORY|PRICE|name|category|price) (.+)$");
            Matcher m=p.matcher(String.join(" ", args));
            if(m.matches()){
                int id = Integer.parseInt(m.group(1));
                String field = m.group(2);
                String value = m.group(3);
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                this.setMessage(productService.prodUpdate(id, field, value));
                result=true;
            }
        }
        return result;
    }
}
