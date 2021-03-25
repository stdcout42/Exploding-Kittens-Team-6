package softwaredesign.gamelogic.commands;

import softwaredesign.gamelogic.Game;
import softwaredesign.gamelogic.cards.CardType;

public class CommandFactory {
    private final Game game;
    public CommandFactory(Game game) {
        this.game = game;
    }

    public Command getCommand(CardType cardType) {
        switch (cardType) {
            case SKIP:
                return  new SkipCommand(game);
            case SEE_THE_FUTURE:
                return new SeeTheFutureCommand(game);
            case REVERSE:
                return new ReverseCommand(game);
            case SHUFFLE:
                return new ShuffleCommand(game);
            case STEAL:
                return new StealCommand(game);
            case DEFUSE:
                return new DefuseCommand(game);
            default:
                return null;
        }
    }
}
