package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

public class StealCommand extends Command {
    public StealCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.playerPlayedSteal();
    }
}
