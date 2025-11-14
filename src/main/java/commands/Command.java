package commands;

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
    public boolean apply(String[] args) {//Personalizable method
        return false;
    }
}
