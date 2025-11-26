package commands.main.user.cash;

import commands.Command;
import model.users.UserService;
/**
 * Command to list all cash registers.
 * Usage: cash list
 * Needs UserService to perform the listing.
 */
public class CashListCommand extends Command {
    private UserService userService;
    public CashListCommand(UserService userService) {
        super("list");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length == 2 && args[1].equals(this.getName())) {
            this.setMessage(userService.cashList());
            result = true;
        }
        return result;
    }
}
