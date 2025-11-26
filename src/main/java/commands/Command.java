package commands;
/**
 * Abstract class representing a command.
 */
public abstract class Command {
    String name;
    String message;


    protected Command (String name) {
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getName() {
        return name;
    }
   /**  * Method to apply the command with given arguments.
     * @param args The arguments for the command.
     * @return A boolean indicating success or failure of the command application.
     */
    public boolean apply(String[] args) {//Personalizable method
        return false;
    }
}
