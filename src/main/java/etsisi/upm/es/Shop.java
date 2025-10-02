package etsisi.upm.es;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hello world!
 */
public class Shop {
    public static void main(String[] args) {
        try{
            FileInputStream file=new FileInputStream("src/main/resources/input.txt");
            System.setIn(file);

            CommandHandler command=new CommandHandler();
            command.init();
            command.start();
            command.end();

            file.close();
        }catch (FileNotFoundException e){
            System.out.println("Error: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
        }

    }
}