package etsisi.upm.es;
/**
 * Interface for Command Line Interface (CLI) implementations.
 */
public interface ICLI {
    void start(CommandHandler commandHandler, String[] args);
}
