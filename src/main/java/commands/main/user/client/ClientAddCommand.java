package commands.main.user.client;

import commands.Command;
import model.users.UserService;

public class ClientAddCommand extends Command {
    private UserService userService;
    public ClientAddCommand(UserService userService) {
        super("add");
        this.userService = userService;
    }
    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[2].equals(this.getName())) {
            // ClientAdd logic to be implemented
            result = true;
        }
        return result;
    }
}
