package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;

public class SeeTheFutureCommand extends Command {
    public SeeTheFutureCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.playerPlayedSeeFuture();
    }
}
