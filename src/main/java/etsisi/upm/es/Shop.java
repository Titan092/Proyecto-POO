package etsisi.upm.es;
import java.io.FileInputStream;
import java.io.IOException;


public class Shop {
    public static void main(String[] args) {
        if(args.length>0){
            String filePath= args[0];
            try{
                FileInputStream file=new FileInputStream(filePath);
                System.setIn(file);
                CommandHandler command=new CommandHandler();
                command.init();
                command.start();
                command.end();
                file.close();
            }catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }else{
            CommandHandler command=new CommandHandler();
            command.init();
            command.start();
            command.end();
        }

    }
}
