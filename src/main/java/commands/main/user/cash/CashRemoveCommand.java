package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

public class CashRemoveCommand extends Command {
    private UserService userService;
    public CashRemoveCommand(UserService userService) {
        super("remove");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 2 && args[2].equals(this.getName())) {
            // CashRemove logic to be implemented
            result = true;
        }
        return result;
    }
}
