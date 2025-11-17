package etsisi.upm.es;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Tests for simple App.
 */
public class AppTest {


    /// Integration test
    @Test
    public void FullAppTest(){
       Scanner s=new Scanner(System.in);
        try{
            FileInputStream file=new FileInputStream("input2");
            System.setIn(file);
            String[] args = new String[0];
            CLI cli = new CLI();
            CommandHandler commandHandler=new CommandHandler();
            cli.start(commandHandler, args);
            file.close();
        }catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}
