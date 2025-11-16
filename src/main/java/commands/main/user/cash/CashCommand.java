package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

import java.util.ArrayList;
import java.util.List;

public class CashCommand extends Command {
    private UserService userService;
    private List<Command> subCommands;
    public CashCommand(UserService userService) {
        super("cash");
        this.userService = userService;
        initSubCommands();
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length >0 && args[0].equals(this.getName())) {
            for(Command cmd : subCommands) {
                if(cmd.apply(args)) {
                    result = true;
                    this.setMessage(cmd.getMessage());
                    break;
                }
            }
        }
        return result;
    }

    private void initSubCommands() {
        subCommands=new ArrayList<>();
        subCommands.add(new CashAddCommand(userService));
        subCommands.add(new CashListCommand(userService));
        subCommands.add(new CashRemoveCommand(userService));
        subCommands.add(new CashTicketsCommand(userService));
    }


}
