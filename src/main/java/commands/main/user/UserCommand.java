package commands.main.user;

import commands.Command;
import commands.main.user.cash.CashCommand;
import commands.main.user.client.ClientCommand;
import model.users.UserService;

import java.util.ArrayList;
import java.util.List;
/**
 * Command to handle user-related operations.
 * It is a root command that delegates to sub-commands like CashCommand and ClientCommand.
 * Needs UserService to perform user operations.
 */
public class UserCommand extends Command {
    private UserService userService;
    private List<Command> subCommands;
    public UserCommand(UserService userService) {
        super("user");
        this.userService = userService;
        initSubCommands();
    }

    private void initSubCommands() {
        subCommands = new ArrayList<>();
        subCommands.add(new CashCommand(userService));
        subCommands.add(new ClientCommand(userService));
    }

    public boolean apply(String[] args) {
        boolean found=false;
        for (Command cmd:subCommands) {
            found=cmd.apply(args);
            if(found){
                this.setMessage(cmd.getMessage());
                break;
            }
        }
        return found;
    }
}
