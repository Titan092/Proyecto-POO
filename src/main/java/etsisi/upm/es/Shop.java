package etsisi.upm.es;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Shop {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        if(args.length>0){
            String filePath= args[0];
            try{
                FileInputStream file=new FileInputStream(filePath);
                System.setIn(file);
                CommandHandler command=new CommandHandler();
                command.init(s);
                command.start();
                command.end();
                file.close();
            }catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }else{
            CommandHandler command=new CommandHandler();
            command.init(s);
            command.start();
            command.end();
        }

    }
}
