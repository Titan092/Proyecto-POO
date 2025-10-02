package etsisi.upm.es;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hello world!
 */
public class Shop {
    public static void main(String[] args) {
        CommandHandler command=new CommandHandler();
        command.init();
        command.start();
        command.end();
    }
}