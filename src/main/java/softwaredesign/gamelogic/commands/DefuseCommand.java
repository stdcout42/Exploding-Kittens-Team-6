package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

public class DefuseCommand extends Command {
    public DefuseCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.playerPlaysDefuseCard();
    }
}
