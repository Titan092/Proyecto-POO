package commands.main.user.cash;

import commands.Command;
import model.users.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashAddCommand extends Command {
    private UserService userService;
    public CashAddCommand(UserService userService) {
        super("add");
        this.userService = userService;
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if(args.length > 2 && args[1].equals(this.getName())) {
            // cash add [<id>] "<nombre>"<email>
            Pattern pattern = Pattern.compile("^cash add (?:(.+))? \"(.+)\"(.+)$");
            Matcher matcher = pattern.matcher(String.join(" ", args));
            if (matcher.matches()) {
                String name = matcher.group(2);
                String email = matcher.group(3);
                String cashId = matcher.group(1);
                if(cashId != null) {
                    userService.cashAdd(cashId, name, email);
                } else {
                    userService.cashAdd(name, email);
                }
                result = true;
            }

        }
        return result;
    }
}
