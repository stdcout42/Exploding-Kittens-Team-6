package softwaredesign.model.cards;

import softwaredesign.model.gamelogic.commands.Command;

public class ExplodingKittenCard extends Card {
    public ExplodingKittenCard(Command command) {
        super(command);
    }

    @Override
    public String toString() {
        return "Exploding Kitten";
    }
}
