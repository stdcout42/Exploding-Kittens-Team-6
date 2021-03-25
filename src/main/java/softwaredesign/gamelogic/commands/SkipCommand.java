package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

public class SkipCommand extends Command{
    public SkipCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.startNextTurn();
    }
}
