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
    public Scanner s;
    private final String PROMPT = "tUPM> ";


    /**
     * Prints a message to the standard output.
     * @param message
     */
    public void printMessage(String message){
        System.out.println(message);
    }

    /**
     * Prints an error message to the standard error output.
     * @param errorMessage
     */
    public void printError(String errorMessage){
        System.err.println(errorMessage);
    }
    /**
     * Reads a command from the standard input.
     * @return The command as a String.
     */
    public String readCommand(){
        return s.nextLine();
    }
    /**
     * Prints a prompt to the standard output.
     * @param prompt
     */
    public void printPrompt(String prompt){
        System.out.print(prompt);
    }
    /**
     * Prints the command if the input is from a file.
     * @param command
     */
    public void printIfIsFileInput(String command){
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
                s=new Scanner(System.in);
                System.out.println(commandHandler.init());
                mainLoop(commandHandler);
                System.out.println(commandHandler.end());
                file.close();
            }catch (IOException e) {
                printError("Error: "+e);
            }
        }else{
            s=new Scanner(System.in);
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
            printPrompt(PROMPT);
            //This part is to differentiate between interactive and non-interactive mode (in the tests)
            //All this is for cleaner output in the tests
            String command = readCommand();
            printIfIsFileInput(command);
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
        printMessage(cleanedMessage+" \n");
    }

}
