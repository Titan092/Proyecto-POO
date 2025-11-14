package etsisi.upm.es;

public class Shop {
    public static void main(String[] args) {
        ICLI cli=new CLI();
        CommandHandler commandHandler=new CommandHandler();
        cli.start(commandHandler, args);
    }
}
