package etsisi.upm.es;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        // Redirigimos la salida estándar a nuestro ByteArrayOutputStream
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restauramos la salida estándar original para no romper otros tests
        System.setOut(originalOut);
    }

    @Test
    public void testOutputHolaMundo() {
        // 1. Ejecutamos el código que imprime algo
        try{
            FileInputStream file=new FileInputStream("input2.txt");
            System.setIn(file);
            String[] args = new String[0];
            CLI cli = new CLI();
            CommandHandler commandHandler=new CommandHandler();
            cli.start(commandHandler, args);
            file.close();
        }catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }

        // 2. Definimos el output esperado
        // Nota: println añade un salto de línea al final, hay que tenerlo en cuenta
        String esperado = "";
        try{
            esperado  = Files.readString(Path.of("Output.txt"));
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
        }

        // 3. Comparamos
        assertEquals(esperado, outContent.toString());
    }
}

