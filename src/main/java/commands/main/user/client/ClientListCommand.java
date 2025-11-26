package commands.main.user.client;

import commands.Command;
import model.users.UserService;
/**
 * Command to list all clients.
 * Usage: client list
 * Needs UserService to perform the listing.
 */
public class ClientListCommand extends Command {
    private UserService userService;
    public ClientListCommand(UserService userService) {
        super("list");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            this.setMessage(userService.clientList());
            result = true;
        }
        return result;
    }
}
