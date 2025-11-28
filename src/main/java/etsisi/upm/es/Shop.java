package etsisi.upm.es;

/**
 * Main class of the Shop application.
 * Initializes the CLI and CommandHandler, and starts the application.
 */
public class Shop {
    public static void main(String[] args) {
        IController cli=new CLI();
        CommandHandler commandHandler=new CommandHandler();
        cli.start(commandHandler, args);
    }
}
