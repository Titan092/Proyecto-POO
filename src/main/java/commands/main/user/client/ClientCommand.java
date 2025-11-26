package commands.main.user.client;

import commands.Command;
import model.users.UserService;

import java.util.ArrayList;
import java.util.List;
/**
 * Command to handle user-related operations.
 * It is a root command that delegates to sub-commands.
 * Needs UserService to perform user operations.
 */
public class ClientCommand extends Command {
    private UserService userService;
    private List<Command> subCommands;
    public ClientCommand(UserService userService) {
        super("client");
        this.userService = userService;
        initSubCommands();
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length >0 && args[0].equals(this.getName())) {
            for(Command cmd:subCommands) {
                result=cmd.apply(args);
                if(result){
                    this.setMessage(cmd.getMessage());
                    break;
                }
            }
        }
        return result;
    }

    private void initSubCommands() {
        subCommands=new ArrayList<>();
        subCommands.add(new ClientAddCommand(userService));
        subCommands.add(new ClientListCommand(userService));
        subCommands.add(new ClientRemoveCommand(userService));
    }


}
