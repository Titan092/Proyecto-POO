package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

public class CashAddCommand extends Command {
    private UserService userService;
    public CashAddCommand(UserService userService) {
        super("add");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 2 && args[1].equals(this.getName())) {
            // CashAdd logic to be implemented
            result = true;
        }
        return result;
    }
}
