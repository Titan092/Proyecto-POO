package commands.main.user.client;

import commands.Command;
import model.users.UserService;

public class ClientRemoveCommand extends Command {
    private UserService userService;
    public ClientRemoveCommand(UserService userService) {
        super("remove");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[2].equals(this.getName())) {
            // ClientRemove logic to be implemented
            result = true;
        }
        return result;
    }
}
