package commands.main.user.client;

import commands.Command;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientRemoveCommand extends Command {
    private UserService userService;
    public ClientRemoveCommand(UserService userService) {
        super("remove");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            Pattern pattern = Pattern.compile("^client remove (.+)$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if (matcher.matches()) {
                String DNI = matcher.group(1);
                this.setMessage(userService.clientRemove(DNI));
                result = true;
            }
        }
        return result;
    }
}
