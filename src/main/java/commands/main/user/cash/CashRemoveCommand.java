package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Command to remove a cash register from a user.
 * Usage: cash remove <cashId>
 * Needs UserService to perform the removal.
 */
public class CashRemoveCommand extends Command {
    private UserService userService;
    public CashRemoveCommand(UserService userService) {
        super("remove");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 2 && args[1].equals(this.getName())) {
            Pattern pattern = Pattern.compile("^cash remove (.+)$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if (matcher.matches()) {
                String cashId = matcher.group(1);
                this.setMessage(userService.cashRemove(cashId));
                result = true;
            }

        }
        return result;
    }
}
