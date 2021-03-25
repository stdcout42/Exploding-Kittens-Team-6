package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

public class ShuffleCommand extends Command {
    public ShuffleCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.shuffleDeck();
    }
}
