package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

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
