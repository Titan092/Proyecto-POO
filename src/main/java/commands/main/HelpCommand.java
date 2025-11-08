package commands.main;

import commands.Command;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    public boolean apply(String[] args) {
        boolean result = false;
        if (args.length == 1 && args[0].equals(this.getName())) {
            printHelp();
            result = true;
        }
        return result;
    }

    public void printHelp() {
        System.out.println("Help Command:");
        System.out.println("Usage: help");
        System.out.println("Displays a list of available commands and their descriptions.");
    }
}
