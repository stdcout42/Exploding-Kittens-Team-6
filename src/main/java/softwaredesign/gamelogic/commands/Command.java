package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;

public abstract class Command {
    protected Game game;

    protected Command(Game game) {
        this.game = game;
    }

    public abstract void execute();
}