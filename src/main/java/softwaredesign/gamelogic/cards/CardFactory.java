package softwaredesign.gamelogic.cards;

import softwaredesign.gamelogic.commands.CommandFactory;
import softwaredesign.gamelogic.Game;

public class CardFactory {
    private final CommandFactory commandFactory;

    public CardFactory(Game game) {
        this.commandFactory = new CommandFactory(game);
    }

    public Card getCard(CardType cardType) {
        switch (cardType) {
            case SKIP:
                return new SkipCard(commandFactory.getCommand(cardType));
            case SEE_THE_FUTURE:
                return new SeeTheFutureCard(commandFactory.getCommand(cardType));
            case REVERSE:
                return new ReverseCard(commandFactory.getCommand(cardType));
            case SHUFFLE:
                return new ShuffleCard(commandFactory.getCommand(cardType));
            case STEAL:
                return new StealCard(commandFactory.getCommand(cardType));
            case DEFUSE:
                return new DefuseCard(commandFactory.getCommand(cardType));
            case EXPLODING_KITTEN:
                return new ExplodingKittenCard(null);
            default:
                return null;
        }
    }
}
