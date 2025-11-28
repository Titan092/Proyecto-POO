package etsisi.upm.es;
/**
 * Interface for Command Line Interface (CLI) implementations.
 */
public interface IController {
    void start(CommandHandler commandHandler, String[] args);
}
