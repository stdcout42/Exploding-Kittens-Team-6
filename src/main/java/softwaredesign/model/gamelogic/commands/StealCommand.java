package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;

public class StealCommand extends Command {
    public StealCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.playerPlayedSteal();
    }
}
