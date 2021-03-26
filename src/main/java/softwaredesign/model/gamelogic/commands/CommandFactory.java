package softwaredesign.model.gamelogic.commands;

import softwaredesign.model.gamelogic.Game;
import softwaredesign.model.cards.CardType;

import java.util.ArrayList;

public class CommandFactory {
    private final ArrayList<Command> commands;
    public CommandFactory(Game game) {
        commands = new ArrayList<>();
        fillCommands(game);
    }

    private void fillCommands(Game game) {
        for (CardType cardType: CardType.values()) {
            switch (cardType) {
                case SKIP:
                    commands.add(new SkipCommand(game));
                    break;
                case SEE_THE_FUTURE:
                    commands.add(new SeeTheFutureCommand(game));
                    break;
                case REVERSE:
                    commands.add(new ReverseCommand(game));
                    break;
                case SHUFFLE:
                    commands.add(new ShuffleCommand(game));
                    break;
                case STEAL:
                    commands.add(new StealCommand(game));
                    break;
                case DEFUSE:
                    commands.add(new DefuseCommand(game));
                    break;
            }
        }
    }

    public Command getCommand(CardType cardType) {
        return commands.get(cardType.ordinal());
    }
}
