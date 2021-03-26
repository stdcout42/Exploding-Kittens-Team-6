package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;

public class SkipCommand extends Command{
    public SkipCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.startNextTurn();
    }
}
