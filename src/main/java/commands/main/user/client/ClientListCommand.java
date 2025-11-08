package commands.main.user.client;

import commands.Command;
import model.users.UserService;

public class ClientListCommand extends Command {
    private UserService userService;
    public ClientListCommand(UserService userService) {
        super("list");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[2].equals(this.getName())) {
            // ClientList logic to be implemented
            result = true;
        }
        return result;
    }
}
