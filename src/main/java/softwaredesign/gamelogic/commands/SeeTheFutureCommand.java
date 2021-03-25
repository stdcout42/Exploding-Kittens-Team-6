package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

public class SeeTheFutureCommand extends Command {
    public SeeTheFutureCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.playerPlayedSeeFuture();
    }
}
