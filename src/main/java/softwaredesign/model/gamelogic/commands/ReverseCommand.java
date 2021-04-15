package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;

public class ReverseCommand extends Command{
    public ReverseCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.flipDirection();
        game.startNextTurn();
    }
}
