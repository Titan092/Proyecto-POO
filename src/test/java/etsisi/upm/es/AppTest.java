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
            FileInputStream file=new FileInputStream("inputExigente.txt");
            System.setIn(file);

            CommandHandler command=new CommandHandler();
            command.init(s);
            command.start();
            command.end();

            file.close();
        }catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}
