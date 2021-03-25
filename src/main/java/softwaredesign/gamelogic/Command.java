package softwaredesign.gamelogic;

public abstract class Command {
    protected Game game;

    protected Command(Game game) {
        this.game = game;
    }
    
    protected abstract void execute();
}