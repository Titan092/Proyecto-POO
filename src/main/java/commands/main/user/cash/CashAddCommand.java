package commands.main.user.cash;

import commands.Command;
import model.users.UserService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Command to add a cash register to a user.
 * Usage: cash add [<id>] "<nombre>" <email>
 * id is optional: UW + 7 digits
 * Needs UserService to perform the addition.
 */
public class CashAddCommand extends Command {
    private UserService userService;
    public CashAddCommand(UserService userService) {
        super("add");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 2 && args[1].equals(this.getName())) {
            // cash add [<id>] "<nombre>" <email>
            // id is optional: UW + 7 digits
            Pattern pattern = Pattern.compile("^cash add(?:\\s+(UW\\d{7}))?\\s+\"([^\"]+)\"\\s+(\\S+)$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if (matcher.matches()) {
                String idGroup = matcher.group(1); // it can be null
                String name = matcher.group(2);
                String email = matcher.group(3);
                if(idGroup != null) {
                    this.setMessage(userService.cashAdd(idGroup, name, email));
                } else {
                    this.setMessage(userService.cashAdd(name, email));
                }
                result = true;
            }
        }
        return result;
    }
}
