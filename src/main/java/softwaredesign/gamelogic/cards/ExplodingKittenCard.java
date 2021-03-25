package softwaredesign.gamelogic.cards;

import softwaredesign.gamelogic.commands.Command;

public class ExplodingKittenCard extends Card {
    public ExplodingKittenCard(Command command) {
        super(command);
    }

    @Override
    public String toString() {
        return "Exploding Kitten";
    }
}
