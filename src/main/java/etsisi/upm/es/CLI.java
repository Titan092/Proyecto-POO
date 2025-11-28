package etsisi.upm.es;

import commands.Command;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Implementation of the Command Line Interface (CLI).
 * It handles user input and output, and interacts with the CommandHandler to process commands.
 */
public class CLI implements IController {
    public static  Scanner s;
    private final String PROMPT = "tUPM> ";

    /**
     * Constructor initializes the scanner for user input.
     */
    public CLI(){
        s=new Scanner(System.in);
    }

    /**
     * Prints a message to the standard output.
     * @param message
     */
    public static void printMessage(String message){
        System.out.println(message);
    }

    /**
     * Prints an error message to the standard error output.
     * @param errorMessage
     */
    public static void printError(String errorMessage){
        System.err.println(errorMessage);
    }
    /**
     * Reads a command from the standard input.
     * @return The command as a String.
     */
    public static String readCommand(){
        return s.nextLine();
    }
    /**
     * Prints a prompt to the standard output.
     * @param prompt
     */
    public static void printPrompt(String prompt){
        System.out.print(prompt);
    }
    /**
     * Prints the command if the input is from a file.
     * @param command
     */
    public static void printIfIsFileInput(String command){
        if(System.in instanceof java.io.FileInputStream){
            System.out.println(command);
        }
    }
    /**
     * Prints the message if it is not null.
     * @param message
     */
    private void printIfIsNotNull(String message){
        if(message!=null){
            System.out.println(message);
        }
    }

    @Override
    /**
     * Starts the CLI with the given CommandHandler and arguments.
     * @param commandHandler
     * @param args if there is an argument, it is treated as a file path to read commands from.
     */
    public void start(CommandHandler commandHandler, String[] args) {
        if(args.length>0){
            String filePath= args[0];
            try{
                FileInputStream file=new FileInputStream(filePath);
                System.setIn(file);
                System.out.println(commandHandler.init());
                mainLoop(commandHandler);
                System.out.println(commandHandler.end());
                file.close();
            }catch (IOException e) {
                CLI.printError("Error: "+e);
            }
        }else{
            System.out.println(commandHandler.init());
            mainLoop(commandHandler);
            System.out.println(commandHandler.end());
        }
    }
    /**
     * Main loop of the CLI that processes user commands.
     * @param commandHandler
     */
    private void mainLoop(CommandHandler commandHandler){
        boolean continues = true;
        while(continues) {
            CLI.printPrompt(PROMPT);
            //This part is to differentiate between interactive and non-interactive mode (in the tests)
            //All this is for cleaner output in the tests
            String command = CLI.readCommand();
            CLI.printIfIsFileInput(command);
            String[] commandUni = command.split(" ");
            Command activeCommand;
            switch (commandUni[0]){
                case "echo":
                    echo(command);
                    break;
                case "exit":
                    continues=false;
                    break;
                default:
                    activeCommand=commandHandler.applyCommand(commandUni);
                    printIfIsNotNull(activeCommand.getMessage());
            }
        }
    }
    /**
     * Echoes the message back to the user.
     * @param message
     */
    protected void echo(String message){
        String cleanedMessage=message.replaceFirst("echo ","");
        CLI.printMessage(cleanedMessage+" \n");
    }

}
