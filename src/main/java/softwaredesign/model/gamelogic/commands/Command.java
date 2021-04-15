package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;

public abstract class Command {
    protected Game game;

    protected Command(Game game) {
        this.game = game;
    }

    public abstract void execute();
}