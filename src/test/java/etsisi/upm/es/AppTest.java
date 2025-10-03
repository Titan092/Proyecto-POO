package etsisi.upm.es;

import static model.Category.BOOK;
import static model.Category.CLOTHES;
import static org.junit.Assert.*;

import model.Ticket;
import org.junit.Test;
import model.Product;
import service.ProductService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */

    @Test
    public void Test1(){
        try{
            FileInputStream file=new FileInputStream("input.txt");
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
