package softwaredesign.gamelogic;

public class SkipCommand extends Command{
    public SkipCommand(Game game) {
        super(game);
    }

    @Override
    protected void execute() {
        // Skip turn in Game
    }
}
