package commands.main.user.client;

import commands.Command;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientAddCommand extends Command {
    private UserService userService;
    public ClientAddCommand(UserService userService) {
        super("add");
        this.userService = userService;
    }
    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 0 && args[1].equals(this.getName())) {
            Pattern pattern = Pattern.compile("^client add \"([^\\\"]+)\\\"\\s+([0-9]{8}[A-Za-z])\\s+([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,})\\s+([A-Za-z0-9_-]+)\\s*$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if (matcher.matches()) {
                String name = matcher.group(1);
                String DNI = matcher.group(2);
                String email = matcher.group(3);
                String cashId= matcher.group(4);
                //userService.addClient(name, DNI, email, CashId);
                result = true;
            }

        }
        return result;
    }
}
