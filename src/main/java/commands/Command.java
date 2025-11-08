package commands;

public abstract class Command {
    String name;

    protected Command (String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public boolean apply(String[] args) {//Personalizable method
        return false;
    }
}
