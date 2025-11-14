package etsisi.upm.es;

import commands.Command;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CLI implements ICLI{
    public static  Scanner s;
    private final String PROMPT = "tUPM> ";
    public CLI(){
        s=new Scanner(System.in);
    }
    public static void printMessage(String message){
        System.out.println(message);
    }

    public static void printError(String errorMessage){
        System.err.println(errorMessage);
    }

    public static String readCommand(){
        return s.nextLine();
    }

    public static void printPrompt(String prompt){
        System.out.print(prompt);
    }

    public static void printIfIsFileInput(String command){
        if(System.in instanceof java.io.FileInputStream){
            System.out.println(command);
        }
    }

    @Override
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
                    System.out.println(activeCommand.getMessage());
            }
        }
    }

    protected void echo(String message){
        CLI.printMessage(message);
    }

}
