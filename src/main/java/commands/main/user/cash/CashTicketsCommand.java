package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

public class CashTicketsCommand extends Command {
    private UserService userService;
    public CashTicketsCommand(UserService userService) {
        super("tickets");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 2 && args[2].equals(this.getName())) {
            // CashTickets logic to be implemented
            result = true;
        }
        return result;
    }
}
