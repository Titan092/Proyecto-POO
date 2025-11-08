package commands;

public abstract class Command {
    String name;

    String getName() {
        return name;
    }
    boolean apply(String[] args) {//Personalizable method
        return false;
    }
}
