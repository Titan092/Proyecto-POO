package commands.main;

import commands.Command;
/**
 * Command to handle unknown commands.
 */
public class UnknownCommand extends Command {
    public UnknownCommand() {
        super("unknown");
        String message="Your command is not contemplated.\n"+
                                 "Type \"help\" for the command guide";
        this.setMessage(message);
    }


}
