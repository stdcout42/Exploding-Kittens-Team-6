package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;

public class DefuseCommand extends Command {
    public DefuseCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.playerPlaysDefuseCard();
    }
}
