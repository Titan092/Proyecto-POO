package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

public class CashListCommand extends Command {
    private UserService userService;
    public CashListCommand(UserService userService) {
        super("list");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length == 2 && args[1].equals(this.getName())) {
            // userService.cashList();
            result = true;
        }
        return result;
    }
}
